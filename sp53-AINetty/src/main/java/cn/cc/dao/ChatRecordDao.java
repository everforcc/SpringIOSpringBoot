package cn.cc.dao;

import cn.cc.entity.ChatRecord;
import java.util.List;

public interface ChatRecordDao {
    List<ChatRecord> findByUserIdAndFriendId(Long userId, Long friendId);
    int insert(ChatRecord chatRecord);
} 