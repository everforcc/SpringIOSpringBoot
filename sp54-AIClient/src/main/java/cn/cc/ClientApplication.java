package cn.cc;

import cn.cc.netty.NettyClient;
import cn.cc.model.Message;
import cn.cc.ui.LoginFrame;
import cn.cc.ui.MainFrame;
import com.alibaba.fastjson2.JSON;
import com.formdev.flatlaf.FlatDarkLaf;  // 新增FlatLaf导入
import javax.swing.*;
import java.awt.*;
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
            // 设置FlatLaf深色主题
            try {
                FlatDarkLaf.setup();
                UIManager.put("Button.arc", 8); // 设置按钮圆角
                UIManager.put("Component.arc", 8); // 设置组件圆角
                UIManager.put("TextComponent.arc", 8); // 设置文本框圆角
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            loginFrame.getLoginButton().addActionListener(e -> {
                // 新增登录按钮加载动画
                loginFrame.getLoginButton().setEnabled(false);
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        // 模拟网络请求延时
                        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                        return null;
                    }
                    @Override
                    protected void done() {
                        loginFrame.getLoginButton().setEnabled(true);
                    }
                }.execute();
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
                                            if (!f.isEmpty()) {
                                                mainFrame.getFriendListModel().addElement(f);
                                            }
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
        // 新增消息发送动画效果
        Component sendBtn = mainFrame.getSendButton();
        sendBtn.setEnabled(false);
        new Timer(100, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count++ < 5) {
                    sendBtn.setBackground(count % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);
                } else {
                    ((Timer)e.getSource()).stop();
                    sendBtn.setBackground(UIManager.getColor("Button.background"));
                    sendBtn.setEnabled(true);
                }
            }
        }).start();
        if (mainFrame == null || currentChatFriend == null) {
            return;
        }
        String text = mainFrame.getInputField().getText();
        if (text == null || text.trim().isEmpty()) {
            return;
        }
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