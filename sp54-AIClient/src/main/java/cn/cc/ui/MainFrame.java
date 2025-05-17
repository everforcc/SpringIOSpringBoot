package cn.cc.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JList<String> friendList;
    private DefaultListModel<String> friendListModel;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton addFriendButton;

    public MainFrame() {
        setTitle("AI 聊天客户端");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 好友列表
        friendListModel = new DefaultListModel<>();
        friendList = new JList<>(friendListModel);
        JScrollPane friendScroll = new JScrollPane(friendList);
        friendScroll.setPreferredSize(new Dimension(150, 0));
        add(friendScroll, BorderLayout.WEST);

        // 聊天区
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("发送");
        addFriendButton = new JButton("添加好友");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(addFriendButton);
        btnPanel.add(sendButton);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(btnPanel, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);
    }

    public JList<String> getFriendList() { return friendList; }
    public DefaultListModel<String> getFriendListModel() { return friendListModel; }
    public JTextArea getChatArea() { return chatArea; }
    public JTextField getInputField() { return inputField; }
    public JButton getSendButton() { return sendButton; }
    public JButton getAddFriendButton() { return addFriendButton; }
} 