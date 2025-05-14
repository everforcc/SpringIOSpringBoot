package cn.cc.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainApp {
    public static void run(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // 设置标签栏UI参数
            UIManager.put("TabbedPane.selected", new Color(230, 240, 250));
            UIManager.put("TabbedPane.tabAreaBackground", new Color(240, 240, 240));
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
            UIManager.put("TabbedPane.tabInsets", new Insets(10, 20, 10, 20));
            UIManager.put("TabbedPane.font", new Font("微软雅黑", Font.BOLD, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AI生成的工具");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            // 设置任务栏图标
            IconSetter.setIcon(frame);

            // 创建主面板
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            // 创建左侧标签栏
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setBackground(new Color(240, 240, 240));
            tabbedPane.setForeground(Color.DARK_GRAY);
            tabbedPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));

            // 自定义标签渲染器
            tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
                @Override
                protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                                  int x, int y, int w, int h, boolean isSelected) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    if (isSelected) {
                        g2d.setColor(new Color(230, 240, 250));
                        g2d.fillRect(x, y, w, h);
                        g2d.setColor(new Color(70, 130, 180));
                        g2d.fillRect(x, y, 3, h);
                    } else {
                        g2d.setColor(new Color(240, 240, 240));
                        g2d.fillRect(x, y, w, h);
                    }
                    g2d.dispose();
                }

                @Override
                protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                    // 不绘制内容边框
                }
            });

            // 创建标签页时使用独立的面板类
            tabbedPane.addTab("首页", new HomePanel());
            tabbedPane.addTab("功能列表", new FunctionPanel());
            tabbedPane.addTab("书架", new BookShelfPanel());  // 新增书架标签页
            tabbedPane.addTab("游戏", new GameListPanel()); // 修改为游戏列表标签页
            tabbedPane.addTab("帮助", new HelpPanel());
            tabbedPane.addTab("退出", new ExitPanel());

            mainPanel.add(tabbedPane, BorderLayout.CENTER);
            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}