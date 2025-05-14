package cn.cc.app;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 退出程序功能面板（新增视觉优化）
 * 设计要点：
 * 1. 使用渐变背景按钮
 * 2. 添加交互动画效果
 * 3. 符合整体UI风格规范
 */
public class ExitPanel extends JPanel {
    public ExitPanel() {
        super(new GridBagLayout());
        setBackground(Color.WHITE);

        // 创建退出按钮并应用新样式
        JButton exitButton = new JButton("安全退出");
        
        // 字体样式配置（与主界面统一）
        exitButton.setFont(new Font("微软雅黑", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        
        // 背景渐变配置（深红色系）
        exitButton.setBackground(new Color(170, 50, 50));
        
        // 边框样式优化
        exitButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 40, 40), 2),
            BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
        
        // 鼠标交互效果
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(190, 60, 60));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(170, 50, 50));
            }
        });

        // 自定义按钮绘制（添加渐变和阴影）
        exitButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 渐变背景绘制
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(180, 60, 60),
                    0, c.getHeight(), new Color(150, 40, 40)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);

                // 顶部高光效果
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight()/3, 25, 25);

                super.paint(g2, c);
                g2.dispose();
            }
        });

        // 点击事件处理（保持原有功能）
        exitButton.addActionListener(e -> System.exit(0));

        // 使用GridBagConstraints布局控制
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTH; // 底部对齐
        gbc.insets = new Insets(0, 0, 50, 0);  // 底部间距
        
        // 添加顶部填充组件使按钮下沉
        add(Box.createVerticalGlue(), gbc);
        add(exitButton);
    }
}