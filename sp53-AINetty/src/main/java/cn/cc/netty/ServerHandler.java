package cn.cc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import cn.cc.model.Message;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import java.util.*;
import cn.cc.dao.impl.UserDaoImpl;
import cn.cc.dao.impl.FriendDaoImpl;
import cn.cc.dao.impl.ChatRecordDaoImpl;
import cn.cc.entity.User;
import cn.cc.entity.Friend;
import cn.cc.entity.ChatRecord;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    // 在线用户映射：账号->Channel
    private static final Map<String, Channel> onlineUsers = new HashMap<>();
    private static final UserDaoImpl userDao = new UserDaoImpl();
    private static final FriendDaoImpl friendDao = new FriendDaoImpl();
    private static final ChatRecordDaoImpl chatRecordDao = new ChatRecordDaoImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到客户端消息: " + msg);
        Message message = null;
        try { message = JSON.parseObject(msg, Message.class); } catch (Exception ignore) {}
        if (message == null) return;
        switch (message.getType()) {
            case "login":
                handleLogin(ctx, message);
                break;
            case "friendList":
                handleFriendList(ctx, message);
                break;
            case "chat":
                handleChat(ctx, message);
                break;
            case "history":
                handleHistory(ctx, message);
                break;
            case "addFriend":
                handleAddFriend(ctx, message);
                break;
            default:
                break;
        }
    }

    private void handleLogin(ChannelHandlerContext ctx, Message msg) {
        User user = userDao.findByAccount(msg.getFrom());
        boolean success = user != null && user.getPassword().equals(msg.getContent());
        if (success) onlineUsers.put(msg.getFrom(), ctx.channel());
        Message resp = new Message();
        resp.setType("loginResp");
        resp.setContent(success ? "success" : "fail");
        ctx.writeAndFlush(JSON.toJSONString(resp));
    }

    private void handleFriendList(ChannelHandlerContext ctx, Message msg) {
        User user = userDao.findByAccount(msg.getFrom());
        if (user == null) return;
        List<Friend> friends = friendDao.findByUserId(user.getId());
        StringBuilder sb = new StringBuilder();
        for (Friend f : friends) {
            User friendUser = userDao.findById(f.getFriendId());
            if (friendUser != null) sb.append(friendUser.getAccount()).append(",");
        }
        Message resp = new Message();
        resp.setType("friendListResp");
        resp.setContent(sb.toString());
        ctx.writeAndFlush(JSON.toJSONString(resp));
    }

    private void handleChat(ChannelHandlerContext ctx, Message msg) {
        User fromUser = userDao.findByAccount(msg.getFrom());
        User toUser = userDao.findByAccount(msg.getTo());
        if (fromUser == null || toUser == null) return;
        // 保存聊天记录
        ChatRecord record = new ChatRecord();
        record.setUserId(fromUser.getId());
        record.setFriendId(toUser.getId());
        record.setMsg(msg.getContent());
        record.setCreateTime(new java.util.Date(msg.getTime() != null ? msg.getTime() : System.currentTimeMillis()));
        chatRecordDao.insert(record);
        // 转发消息
        Channel toChannel = onlineUsers.get(msg.getTo());
        if (toChannel != null) {
            toChannel.writeAndFlush(JSON.toJSONString(msg));
        }
        // 回显给自己
        ctx.writeAndFlush(JSON.toJSONString(msg));
    }

    private void handleHistory(ChannelHandlerContext ctx, Message msg) {
        User user = userDao.findByAccount(msg.getFrom());
        User friend = userDao.findByAccount(msg.getTo());
        if (user == null || friend == null) return;
        List<ChatRecord> records = chatRecordDao.findByUserIdAndFriendId(user.getId(), friend.getId());
        StringBuilder sb = new StringBuilder();
        for (ChatRecord r : records) {
            sb.append(r.getMsg()).append(",");
        }
        Message resp = new Message();
        resp.setType("historyResp");
        resp.setContent(sb.toString());
        ctx.writeAndFlush(JSON.toJSONString(resp));
    }

    private void handleAddFriend(ChannelHandlerContext ctx, Message msg) {
        User user = userDao.findByAccount(msg.getFrom());
        User friend = userDao.findByAccount(msg.getTo());
        Message resp = new Message();
        resp.setType("addFriendResp");
        if (user != null && friend != null) {
            Friend f1 = new Friend();
            f1.setUserId(user.getId());
            f1.setFriendId(friend.getId());
            Friend f2 = new Friend();
            f2.setUserId(friend.getId());
            f2.setFriendId(user.getId());
            friendDao.insert(f1);
            friendDao.insert(f2);
            resp.setContent("success");
        } else {
            resp.setContent("fail");
        }
        ctx.writeAndFlush(JSON.toJSONString(resp));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
} 