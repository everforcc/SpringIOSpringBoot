package cn.cc.service;

import cn.cc.entity.ChatRecord;
import java.util.List;

public interface ChatRecordService {
    boolean saveRecord(ChatRecord chatRecord);
    List<ChatRecord> getRecords(Long userId, Long friendId);
} 