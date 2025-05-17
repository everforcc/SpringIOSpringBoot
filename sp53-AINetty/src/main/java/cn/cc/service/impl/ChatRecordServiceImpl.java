package cn.cc.service.impl;

import cn.cc.dao.ChatRecordDao;
import cn.cc.entity.ChatRecord;
import cn.cc.service.ChatRecordService;
import java.util.List;

public class ChatRecordServiceImpl implements ChatRecordService {
    private final ChatRecordDao chatRecordDao;

    public ChatRecordServiceImpl(ChatRecordDao chatRecordDao) {
        this.chatRecordDao = chatRecordDao;
    }

    @Override
    public boolean saveRecord(ChatRecord chatRecord) {
        return chatRecordDao.insert(chatRecord) > 0;
    }

    @Override
    public List<ChatRecord> getRecords(Long userId, Long friendId) {
        return chatRecordDao.findByUserIdAndFriendId(userId, friendId);
    }
} 