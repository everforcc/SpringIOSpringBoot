package cn.cc;

import cn.cc.netty.NettyClient;
import cn.cc.model.Message;
import cn.cc.ui.LoginFrame;
import cn.cc.ui.MainFrame;
import com.alibaba.fastjson2.JSON;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ClientApplication {
    private static NettyClient nettyClient;
    private static String currentUser;
    private static MainFrame mainFrame;
    private static List<String> friends = new ArrayList<>();
    private static String currentChatFriend = null;

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            loginFrame.getLoginButton().addActionListener(e -> {
                String account = loginFrame.getAccount();
                String password = loginFrame.getPassword();
                Message loginMsg = new Message();
                loginMsg.setType("login");
                loginMsg.setFrom(account);
                loginMsg.setContent(password);
                new Thread(() -> {
                    try {
                        nettyClient = new NettyClient("127.0.0.1", 8080, msg -> {
                            final Message message;
                            try { message = com.alibaba.fastjson2.JSON.parseObject(msg, Message.class); } catch (Exception ignore) { return; }
                            if (message != null && "loginResp".equals(message.getType())) {
                                if ("success".equals(message.getContent())) {
                                    currentUser = account;
                                    SwingUtilities.invokeLater(() -> {
                                        loginFrame.dispose();
                                        mainFrame = new MainFrame();
                                        mainFrame.setVisible(true);
                                        // 拉取好友列表
                                        Message friendReq = new Message();
                                        friendReq.setType("friendList");
                                        friendReq.setFrom(currentUser);
                                        nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(friendReq));
                                        // 绑定聊天发送事件
                                        mainFrame.getSendButton().addActionListener(ev -> sendChatMsg());
                                        mainFrame.getFriendList().addListSelectionListener(ev -> {
                                            if (!ev.getValueIsAdjusting()) {
                                                currentChatFriend = mainFrame.getFriendList().getSelectedValue();
                                                mainFrame.getChatArea().setText("");
                                            }
                                        });
                                        // 绑定添加好友事件
                                        mainFrame.getAddFriendButton().addActionListener(ev -> {
                                            String friendAccount = JOptionPane.showInputDialog(mainFrame, "请输入要添加的好友账号：");
                                            if (friendAccount != null && !friendAccount.trim().isEmpty()) {
                                                Message addMsg = new Message();
                                                addMsg.setType("addFriend");
                                                addMsg.setFrom(currentUser);
                                                addMsg.setTo(friendAccount.trim());
                                                nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(addMsg));
                                            }
                                        });
                                    });
                                } else {
                                    JOptionPane.showMessageDialog(loginFrame, "登录失败！");
                                }
                            } else if (message != null && "friendListResp".equals(message.getType())) {
                                // 展示好友列表
                                SwingUtilities.invokeLater(() -> {
                                    mainFrame.getFriendListModel().clear();
                                    if (message.getContent() != null) {
                                        String[] arr = message.getContent().split(",");
                                        for (String f : arr) {
                                            if (!f.isEmpty()) mainFrame.getFriendListModel().addElement(f);
                                        }
                                    }
                                });
                            } else if (message != null && "chat".equals(message.getType())) {
                                // 展示聊天消息
                                if (message.getFrom() != null && message.getFrom().equals(currentChatFriend)) {
                                    SwingUtilities.invokeLater(() -> {
                                        mainFrame.getChatArea().append(message.getFrom() + ": " + message.getContent() + "\n");
                                    });
                                }
                            } else if (message != null && "addFriendResp".equals(message.getType())) {
                                if ("success".equals(message.getContent())) {
                                    JOptionPane.showMessageDialog(mainFrame, "添加好友成功！");
                                    // 刷新好友列表
                                    Message friendReq = new Message();
                                    friendReq.setType("friendList");
                                    friendReq.setFrom(currentUser);
                                    nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(friendReq));
                                } else {
                                    JOptionPane.showMessageDialog(mainFrame, "添加好友失败！");
                                }
                            }
                        });
                        nettyClient.startWithFirstMsg(com.alibaba.fastjson2.JSON.toJSONString(loginMsg));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            });
        });
    }

    private static void sendChatMsg() {
        if (mainFrame == null || currentChatFriend == null) return;
        String text = mainFrame.getInputField().getText();
        if (text == null || text.trim().isEmpty()) return;
        Message chatMsg = new Message();
        chatMsg.setType("chat");
        chatMsg.setFrom(currentUser);
        chatMsg.setTo(currentChatFriend);
        chatMsg.setContent(text);
        chatMsg.setTime(System.currentTimeMillis());
        nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(chatMsg));
        mainFrame.getChatArea().append(currentUser + ": " + text + "\n");
        mainFrame.getInputField().setText("");
    }
} 