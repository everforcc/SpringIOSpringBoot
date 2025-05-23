package cn.cc.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox rememberPasswordBox;
    private JLabel statusLabel;

    public LoginFrame() {
        setTitle("AI 聊天 - 登录");
        setSize(400, 450); // 将高度从350增加到450
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 创建主面板并设置边距
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setContentPane(mainPanel);
        
        // 标题面板
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("AI 聊天", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(new Color(64, 128, 255));
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        
        // 副标题
        JLabel subtitleLabel = new JLabel("在线沟通，畅所欲言", JLabel.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // 登录表单面板
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // 账号输入
        JPanel accountPanel = new JPanel(new BorderLayout(10, 0));
        accountPanel.setBorder(new EmptyBorder(5, 0, 15, 0));
        JLabel accountLabel = new JLabel("账号");
        accountLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        accountField = new JTextField();
        accountField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        accountField.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, new Color(64, 128, 255)),
            BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        accountPanel.add(accountLabel, BorderLayout.NORTH);
        accountPanel.add(accountField, BorderLayout.CENTER);
        
        // 密码输入
        JPanel passwordPanel = new JPanel(new BorderLayout(10, 0));
        passwordPanel.setBorder(new EmptyBorder(5, 0, 25, 0));
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordField.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, new Color(64, 128, 255)),
            BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // 添加到表单面板
        formPanel.add(accountPanel);
        formPanel.add(passwordPanel);
        
        // 添加记住密码选项
        rememberPasswordBox = new JCheckBox("记住密码");
        rememberPasswordBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        rememberPasswordBox.setForeground(new Color(150, 150, 150));
        formPanel.add(rememberPasswordBox);
        
        // 添加状态提示标签
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(150, 150, 150));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(statusLabel);
        
        // 登录按钮
        loginButton = new JButton("登 录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        // 使用更柔和的颜色
        loginButton.setBackground(new Color(75, 139, 190));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 添加按钮悬停效果 - 使用更柔和的过渡色
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(95, 159, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(75, 139, 190));
            }
        });
        
        // 添加回车键登录支持
        InputMap inputMap = loginButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "login");
        loginButton.getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                loginButton.doClick();
            }
        });
        
        formPanel.add(loginButton);
        
        // 将表单面板添加到主面板
        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    public String getAccount() {
        return accountField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public JButton getLoginButton() {
        return loginButton;
    }
    
    public void setLoginStatus(String status) {
        statusLabel.setText(status);
    }
    
    public boolean isRememberPassword() {
        return rememberPasswordBox.isSelected();
    }
}