package cn.cc.envcontrol;

// 引入Lombok依赖
import lombok.Data;

// 工作日志类，用于记录系统的工作日志信息
@Data // 使用Lombok的@Data注解
public class WorkLog {
    // 日志ID
    private int id;

    // 安防，消防，配电
    private String type;

    // 日志名称
    private String name;
    // 日志描述
    private String description;
    // 日志日期
    private String date;
    // 日志状态（例如：未解决、已解决）
    private String status;
    // 操作（例如：编辑、删除）
//    private String operation;

}
