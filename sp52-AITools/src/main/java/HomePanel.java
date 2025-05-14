import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel("AI生成的各种小工具");
        label.setFont(new Font("微软雅黑", Font.BOLD, 18));
        label.setForeground(new Color(70, 130, 180));
        add(label);
    }
}