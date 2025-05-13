import javax.swing.*;
import java.awt.*;

public class ExitPanel extends JPanel {
    public ExitPanel() {
        super(new GridBagLayout());
        setBackground(Color.WHITE);

        // 创建退出按钮
        JButton exitButton = new JButton("退出程序");
        exitButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setFocusPainted(false);
        
        // 添加点击事件
        exitButton.addActionListener(e -> System.exit(0));

        // 使用GridBagConstraints布局控制
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTH; // 底部对齐
        gbc.insets = new Insets(0, 0, 50, 0);  // 底部间距
        
        // 添加顶部填充组件使按钮下沉
        add(Box.createVerticalGlue(), gbc);
        add(exitButton, gbc);
        add(Box.createVerticalStrut(20), gbc); // 底部留白
    }
}