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
import java.util.Timer;        // Add this import
import java.util.TimerTask;    // Add this import

public class ClientApplication {
    private static NettyClient nettyClient;
    private static String currentUser;
    private static MainFrame mainFrame;
    private static List<String> friends = new ArrayList<>();
    private static String currentChatFriend = null;
    private static final long HEARTBEAT_INTERVAL = 30000; // 30秒
    private static Timer heartbeatTimer;
    private static int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 5;

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        SwingUtilities.invokeLater(() -> {
            // 设置FlatLaf深色主题
            try {
                FlatDarkLaf.setup();
                UIManager.put("Button.arc", 10); // 设置按钮圆角
                UIManager.put("Component.arc", 10); // 设置组件圆角
                UIManager.put("TextComponent.arc", 10); // 设置文本框圆角
                
                // 添加更多美化设置
                UIManager.put("Button.margin", new Insets(8, 14, 8, 14)); // 按钮内边距
                UIManager.put("TabbedPane.showTabSeparators", true); // 显示选项卡分隔符
                UIManager.put("ScrollBar.width", 12); // 滚动条宽度
                UIManager.put("ScrollBar.thumbArc", 999); // 滚动条滑块圆角
                UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2)); // 滚动条滑块内边距
                UIManager.put("TextField.margin", new Insets(6, 6, 6, 6)); // 文本框内边距
                
                // 设置字体
                Font defaultFont = new Font("微软雅黑", Font.PLAIN, 14);
                UIManager.put("Button.font", defaultFont);
                UIManager.put("Label.font", defaultFont);
                UIManager.put("TextField.font", defaultFont);
                UIManager.put("TextArea.font", defaultFont);
                UIManager.put("List.font", defaultFont);
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
                        nettyClient = new NettyClient("8.146.199.165", 9090, msg -> {
                            final Message message;
                            try { message = com.alibaba.fastjson2.JSON.parseObject(msg, Message.class); } catch (Exception ignore) { return; }
                            if (message != null && "loginResp".equals(message.getType())) {
                                if ("success".equals(message.getContent())) {
                                    currentUser = account;
                                    SwingUtilities.invokeLater(() -> {
                                        loginFrame.dispose();
                                        mainFrame = new MainFrame();
                                        mainFrame.setVisible(true);
                                        mainFrame.setCurrentUser(currentUser); // 设置当前用户
                                        
                                        // 拉取好友列表
                                        Message friendReq = new Message();
                                        friendReq.setType("friendList");
                                        friendReq.setFrom(currentUser);
                                        nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(friendReq));
                                        
                                        // 绑定聊天发送事件
                                        mainFrame.getSendButton().addActionListener(ev -> sendChatMsg());
                                        
                                        // 让回车键发送消息
                                        mainFrame.getInputField().addActionListener(ev -> sendChatMsg());
                                        
                                        mainFrame.getFriendList().addListSelectionListener(ev -> {
                                            if (!ev.getValueIsAdjusting()) {
                                                String selectedFriend = mainFrame.getFriendList().getSelectedValue();
                                                if (selectedFriend != null && !selectedFriend.equals(currentChatFriend)) {
                                                    currentChatFriend = selectedFriend;
                                                    // 清空聊天区域
                                                    mainFrame.getMessagePanel().removeAll();
                                                    mainFrame.getMessagePanel().revalidate();
                                                    mainFrame.getMessagePanel().repaint();
                                                }
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
                                        // 使用新的addMessage方法显示消息
                                        mainFrame.addMessage(message.getFrom(), message.getContent(), false);
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
        startHeartbeat();
    }
    
    private static void startHeartbeat() {
        heartbeatTimer = new Timer();
        heartbeatTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (nettyClient != null) {
                    Message heartbeat = new Message();
                    heartbeat.setType("heartbeat");
                    heartbeat.setFrom(currentUser);
                    heartbeat.setTime(System.currentTimeMillis());
                    nettyClient.send(com.alibaba.fastjson2.JSON.toJSONString(heartbeat));
                }
            }
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL);
    }
    
    private static void reconnect() {
        if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            reconnectAttempts++;
            try {
                Thread.sleep(1000 * reconnectAttempts); // 递增重连延迟
                // 重新连接逻辑
                // ... 实现重连逻辑 ...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "连接服务器失败，请检查网络后重试！");
        }
    }
    
    private static void sendChatMsg() {
        // 新增消息发送动画效果
        Component sendBtn = mainFrame.getSendButton();
        sendBtn.setEnabled(false);
        javax.swing.Timer animationTimer = new javax.swing.Timer(100, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count++ < 5) {
                    sendBtn.setBackground(count % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);
                } else {
                    ((javax.swing.Timer)e.getSource()).stop();
                    sendBtn.setBackground(UIManager.getColor("Button.background"));
                    sendBtn.setEnabled(true);
                }
            }
        });
        animationTimer.start();  // 需要显式调用start()方法
        
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
        
        // 使用新的addMessage方法显示自己的消息
        mainFrame.addMessage(currentUser, text, true);
        
        // 清空输入框
        mainFrame.getInputField().setText("");
    }
}