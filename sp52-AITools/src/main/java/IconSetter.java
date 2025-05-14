import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class IconSetter {
    /**
     * 设置窗口的图标。
     *
     * @param frame 要设置图标的窗口。
     */
    public static void setIcon(JFrame frame) {
        try {
            InputStream inputStream = IconSetter.class.getResourceAsStream("/img.png");
            if (inputStream != null) {
                BufferedImage image = ImageIO.read(inputStream);
                if (image != null) {
                    frame.setIconImage(new ImageIcon(image).getImage());
                } else {
                    System.err.println("Failed to read image from stream.");
                }
            } else {
                System.err.println("Resource not found: /img.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}