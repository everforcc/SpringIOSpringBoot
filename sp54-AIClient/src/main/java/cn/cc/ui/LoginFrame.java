package cn.cc.ui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("登录");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel accountLabel = new JLabel("账号:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(accountLabel, gbc);
        accountField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        add(accountField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(passwordField, gbc);

        loginButton = new JButton("登录");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(loginButton, gbc);
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
} 