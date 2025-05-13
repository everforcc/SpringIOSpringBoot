import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Launcher {
    public static void main(String[] args) {
        try {
            MainApp.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}