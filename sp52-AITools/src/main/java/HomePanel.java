import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel("欢迎来到应用程序首页");
        label.setFont(new Font("微软雅黑", Font.BOLD, 18));
        label.setForeground(new Color(70, 130, 180));
        add(label);
    }
}