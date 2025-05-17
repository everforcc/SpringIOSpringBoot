package cn.cc.dao;

import cn.cc.entity.Friend;
import java.util.List;

public interface FriendDao {
    List<Friend> findByUserId(Long userId);
    int insert(Friend friend);
    int delete(Long userId, Long friendId);
} 