# 布防/撤防 时序图与交互图

## 判定是否上报（shouldReport）- 时序图
```mermaid
sequenceDiagram
    autonumber
    participant D as 设备/调用方
    participant C as ArmingController
    participant S as ArmingService
    participant LC1 as 本地缓存(设备→组)
    participant LC2 as 本地缓存(组周位图/今日掩码)
    participant R as Redis
    participant DB as MySQL

    D->>C: GET /arming/shouldReport/{deviceId}
    C->>S: shouldReport(deviceId)
    S->>LC1: 读取 deviceId→groupId
    alt 未命中
        S->>R: HGET dev:gid deviceId
        alt 未命中
            S->>DB: SELECT group_id FROM device WHERE id=?
            DB-->>S: groupId 或 null
            opt 命中DB
                S->>R: HSET dev:gid deviceId groupId
            end
        end
        S->>LC1: 回填本地缓存
    end

    S->>LC2: 读取 group 周位图 BitSet
    alt 未命中
        S->>R: GET arm:sch:g:groupId (42字节)
        alt 未命中
            S->>DB: SELECT week_bits FROM arming_group_schedule WHERE group_id=?
            DB-->>S: week_bits 或 null
            opt 命中DB
                S->>R: SET arm:sch:g:groupId week_bits
            end
        end
        S->>LC2: 回填本地缓存
    end

    Note over S: 计算 daySlot(0..47) 与 weekOffset(0..335)
    S-->>S: baseArmed = weekBits[weekOffset]
    alt baseArmed==false
        S-->>C: false
        C-->>D: {shouldReport:false}
    else baseArmed==true
        S->>LC2: 读取今日掩码 BitSet(48位)
        alt 未命中
            S->>R: GET arm:mask:today:g:groupId (6字节)
            S->>LC2: 回填本地缓存
        end
        S-->>S: armed = baseArmed && !todayMask[daySlot]
        S-->>C: armed
        C-->>D: {shouldReport:armed}
    end
```

## 今日撤防（defuse）- 时序图
```mermaid
sequenceDiagram
    autonumber
    participant U as 用户/调用方
    participant C as ArmingController
    participant S as ArmingService
    participant LC as 本地缓存(周位图/今日掩码)
    participant R as Redis

    U->>C: POST /arming/defuse/{groupId}
    C->>S: defuseToday(groupId)
    S->>LC: 取周位图 BitSet（若无则从Redis/DB加载）
    S-->>S: 截取当天 dayBits(48位)
    S-->>S: 根据当前时间构建撤防掩码 mask(48位)
    S->>R: GET arm:mask:today:g:groupId（已有掩码）
    S-->>S: existedMask OR mask
    S->>R: SET arm:mask:today:g:groupId existedMask TTL=至午夜
    S->>LC: 合并并更新本地掩码
    S-->>C: OK
    C-->>U: {status:OK}
```

## 组件交互图（读/写路径）
```mermaid
graph TD
    A[Client/设备] -->|HTTP| B(ArmingController)
    B --> C(ArmingService)
    C -->|本地命中| L1[本地缓存: 设备→组]
    C -->|本地命中| L2[本地缓存: 周位图/今日掩码]
    C -->|HGET/GET/SET| R[Redis]
    C -->|回源/写通| M[MySQL]

    subgraph 读路径
    L1 --> C
    L2 --> C
    R --> C
    M --> C
    end

    subgraph 写通路径
    C --> M
    C --> R
    C --> L1
    C --> L2
    end
```

## 时间槽与偏移计算
- daySlot = hour × 2 + (minute ≥ 30 ? 1 : 0) ∈ [0,47]
- weekDayIndex = (Mon=1..Sun=7) % 7 ∈ [0,6]
- weekOffset = weekDayIndex × 48 + daySlot ∈ [0,335]

> 注：Asia/Shanghai 时区，午夜自动切换，今日掩码 TTL 设为“距午夜剩余时长”。
