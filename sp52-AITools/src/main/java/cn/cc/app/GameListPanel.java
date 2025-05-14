package cn.cc.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameListPanel extends JPanel {
    public GameListPanel() {
        // 使用与功能列表相同的布局
        setLayout(new GridLayout(0, 2, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(245, 245, 245));

        // 创建游戏按钮
        JButton snakeGameButton = createGameButton("贪吃蛇", "/snake.png");
        snakeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gameFrame = new JFrame("贪吃蛇");
                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                gameFrame.setSize(600, 500);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.add(new SnakeGamePanel());
                gameFrame.setVisible(true);
            }
        });

        add(snakeGameButton);

        // 添加更多游戏按钮
        // JButton anotherGameButton = createGameButton("另一个游戏", "icons/another.png");
        // add(anotherGameButton);
    }

    private JButton createGameButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14)); // 修改字体大小
        button.setForeground(Color.BLACK); // 修改字体颜色
        button.setBackground(new Color(240, 240, 240)); // 修改背景颜色
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // 修改边框样式
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 120)); // 设置按钮大小

        // 添加图标
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)); // 缩小图标大小
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220)); // 修改悬停背景颜色
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240)); // 恢复背景颜色
            }
        });

        return button;
    }
}