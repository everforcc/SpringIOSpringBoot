package cn.cc.service.impl;

import cn.cc.dao.FriendDao;
import cn.cc.entity.Friend;
import cn.cc.service.FriendService;
import java.util.List;

public class FriendServiceImpl implements FriendService {
    private final FriendDao friendDao;

    public FriendServiceImpl(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public boolean addFriend(Long userId, Long friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        return friendDao.insert(friend) > 0;
    }

    @Override
    public boolean removeFriend(Long userId, Long friendId) {
        return friendDao.delete(userId, friendId) > 0;
    }

    @Override
    public List<Friend> getFriends(Long userId) {
        return friendDao.findByUserId(userId);
    }
} 