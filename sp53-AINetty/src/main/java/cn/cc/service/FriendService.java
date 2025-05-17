package cn.cc.service;

import cn.cc.entity.Friend;
import java.util.List;

public interface FriendService {
    boolean addFriend(Long userId, Long friendId);
    boolean removeFriend(Long userId, Long friendId);
    List<Friend> getFriends(Long userId);
} 