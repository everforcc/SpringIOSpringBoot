package cn.cc.dao.impl;

import cn.cc.dao.FriendDao;
import cn.cc.entity.Friend;
import cn.cc.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDaoImpl implements FriendDao {
    @Override
    public List<Friend> findByUserId(Long userId) {
        List<Friend> list = new ArrayList<>();
        String sql = "SELECT * FROM friend WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Friend f = new Friend();
                f.setId(rs.getLong("id"));
                f.setUserId(rs.getLong("user_id"));
                f.setFriendId(rs.getLong("friend_id"));
                list.add(f);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int insert(Friend friend) {
        String sql = "INSERT INTO friend(user_id, friend_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, friend.getUserId());
            ps.setLong(2, friend.getFriendId());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int delete(Long userId, Long friendId) {
        String sql = "DELETE FROM friend WHERE user_id=? AND friend_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
} 