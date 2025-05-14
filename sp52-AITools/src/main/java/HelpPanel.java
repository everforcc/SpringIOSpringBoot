import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class HelpPanel extends JPanel {
    public HelpPanel() {
        super(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // 创建帮助内容面板（新增邮件支持） support@aitools.com
        JTextPane helpContent = new JTextPane();
        helpContent.setContentType("text/html");
        helpContent.setText("<html><body style='padding:20px'>"
                + "<h2>帮助文档</h2>"
                + "<p>常见问题解答...</p>"
                + "<p>技术支持邮箱：<a href='mailto:718497737@qq.com'>718497737@qq.com</a></p>"
                + "</body></html>");
        helpContent.setEditable(false);
        
        // 设置邮箱链接样式和交互
        JLabel emailLabel = new JLabel("<html><a href='mailto:718497737@qq.com'>718497737@qq.com</a></html>");
        emailLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        emailLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:718497737@qq.com"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(HelpPanel.this, 
                        "无法启动邮件客户端", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        emailLabel.setForeground(new Color(70, 130, 180));
        emailLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 布局调整（新增底部邮箱面板）
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(new JLabel("联系我们："));
        bottomPanel.add(emailLabel);

        add(new JScrollPane(helpContent), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}