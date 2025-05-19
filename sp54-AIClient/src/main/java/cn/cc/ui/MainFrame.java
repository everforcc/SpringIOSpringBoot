package cn.cc.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
    private JList<String> friendList;
    private DefaultListModel<String> friendListModel;
    private JPanel chatPanel;
    private JScrollPane chatScrollPane;
    private JTextField inputField;
    private JButton sendButton;
    private JButton addFriendButton;
    private String currentUser;

    // 使用JPanel代替JTextArea来实现更复杂的聊天布局
    private JPanel messagePanel;
    private JScrollPane messageScrollPane;
    
    public MainFrame() {
        setTitle("AI 聊天客户端");
        setSize(900, 600);  // 调大界面尺寸
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));  // 添加间距
        
        // 创建分隔面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);  // 设置分隔位置
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        
        // 好友列表面板
        JPanel friendPanel = new JPanel(new BorderLayout(0, 5));
        friendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        // 添加标题
        JLabel friendTitle = new JLabel("联系人", JLabel.CENTER);
        friendTitle.setFont(new Font("微软雅黑", Font.BOLD, 16));
        friendTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        friendPanel.add(friendTitle, BorderLayout.NORTH);
        
        // 好友列表
        friendListModel = new DefaultListModel<>();
        friendList = new JList<>(friendListModel);
        friendList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friendList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // 自定义好友列表渲染器
        friendList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                return label;
            }
        });
        
        JScrollPane friendScroll = new JScrollPane(friendList);
        friendScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        friendPanel.add(friendScroll, BorderLayout.CENTER);
        
        // 添加好友按钮
        addFriendButton = new JButton("添加好友");
        addFriendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addFriendButton.setFocusPainted(false);
        friendPanel.add(addFriendButton, BorderLayout.SOUTH);
        
        // 添加到分隔面板
        splitPane.setLeftComponent(friendPanel);
        
        // 聊天区域
        chatPanel = new JPanel(new BorderLayout(0, 5));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        
        // 消息面板 - 使用垂直BoxLayout排列消息
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messagePanel.setBackground(Color.WHITE);
        
        messageScrollPane = new JScrollPane(messagePanel);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        chatPanel.add(messageScrollPane, BorderLayout.CENTER);
        
        // 输入区域
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputField = new JTextField();
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        sendButton = new JButton("发送");
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sendButton.setFocusPainted(false);
        sendButton.setPreferredSize(new Dimension(80, 36));
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        
        // 添加到分隔面板
        splitPane.setRightComponent(chatPanel);
        
        // 添加分隔面板到主窗口
        add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * 添加消息到聊天面板
     * @param sender 发送者
     * @param content 消息内容
     * @param isSelf 是否是自己发送的
     */
    public void addMessage(String sender, String content, boolean isSelf) {
        // 创建一条消息的面板
        JPanel messageContainer = new JPanel();
        messageContainer.setLayout(new BorderLayout());
        messageContainer.setOpaque(false);
        
        // 创建消息气泡
        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BorderLayout());
        
        // 根据是否自己发送设置不同的样式
        if (isSelf) {
            messageContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
            bubblePanel.setBackground(new Color(64, 128, 255)); // 蓝色背景
            bubblePanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(64, 128, 255), 15),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
            ));
        } else {
            messageContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
            bubblePanel.setBackground(new Color(240, 240, 240)); // 灰色背景
            bubblePanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(240, 240, 240), 15),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
            ));
        }
        
        // 消息文本
        JLabel messageLabel = new JLabel("<html><body style='width: 250px'>" + content + "</body></html>");
        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        messageLabel.setForeground(isSelf ? Color.WHITE : Color.BLACK);
        bubblePanel.add(messageLabel, BorderLayout.CENTER);
        
        // 添加时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        JLabel timeLabel = new JLabel(sdf.format(new Date()));
        timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);
        
        // 创建包含发送者名称和气泡的面板
        JPanel contentPanel = new JPanel(new BorderLayout(5, 3));
        contentPanel.setOpaque(false);
        
        // 添加发送者名字
        JLabel senderLabel = new JLabel(sender);
        senderLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
        
        if (isSelf) {
            contentPanel.add(timeLabel, BorderLayout.WEST);
            contentPanel.add(bubblePanel, BorderLayout.EAST);
            contentPanel.add(senderLabel, BorderLayout.NORTH);
        } else {
            contentPanel.add(timeLabel, BorderLayout.EAST);
            contentPanel.add(bubblePanel, BorderLayout.WEST);
            contentPanel.add(senderLabel, BorderLayout.NORTH);
        }
        
        messageContainer.add(contentPanel);
        
        // 添加一些间距
        messagePanel.add(Box.createVerticalStrut(10));
        // 添加消息到面板
        messagePanel.add(messageContainer);
        
        // 滚动到最新的消息
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = messageScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
        
        // 更新UI
        messagePanel.revalidate();
        messagePanel.repaint();
    }
    
    // 圆角边框内部类
    private class RoundedBorder extends AbstractBorder {
        private Color color;
        private int radius;
        
        RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    
    public String getCurrentUser() {
        return currentUser;
    }

    public JList<String> getFriendList() { return friendList; }
    public DefaultListModel<String> getFriendListModel() { return friendListModel; }
    public JTextField getInputField() { return inputField; }
    public JButton getSendButton() { return sendButton; }
    public JButton getAddFriendButton() { return addFriendButton; }
    
    // 替代原来的getChatArea方法
    public JPanel getMessagePanel() { return messagePanel; }
    public JScrollPane getMessageScrollPane() { return messageScrollPane; }
} 