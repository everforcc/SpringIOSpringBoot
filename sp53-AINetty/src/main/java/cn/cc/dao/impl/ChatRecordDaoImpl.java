package cn.cc.dao.impl;

import cn.cc.dao.ChatRecordDao;
import cn.cc.entity.ChatRecord;
import cn.cc.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRecordDaoImpl implements ChatRecordDao {
    @Override
    public List<ChatRecord> findByUserIdAndFriendId(Long userId, Long friendId) {
        List<ChatRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM chat_record WHERE user_id=? AND friend_id=? ORDER BY create_time ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatRecord cr = new ChatRecord();
                cr.setId(rs.getLong("id"));
                cr.setUserId(rs.getLong("user_id"));
                cr.setFriendId(rs.getLong("friend_id"));
                cr.setMsg(rs.getString("msg"));
                cr.setCreateTime(rs.getTimestamp("create_time"));
                list.add(cr);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int insert(ChatRecord chatRecord) {
        String sql = "INSERT INTO chat_record(user_id, friend_id, msg, create_time) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, chatRecord.getUserId());
            ps.setLong(2, chatRecord.getFriendId());
            ps.setString(3, chatRecord.getMsg());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
} 