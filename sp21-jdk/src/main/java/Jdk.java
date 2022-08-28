import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Jdk {

    public static void main(String[] args) {
        //System.out.println("jdk基础知识");
        try {
            Desktop.getDesktop().open(new File("ftp:127.0.0.1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
