package cn.cc.bio.single;

import cn.cc.bio.BIOConstant;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class BIOClient {

    public static void main(String[] args) {
        runClient();
    }

    private static void runClient() {

        Socket socket = null;
        try {
            // 连接
            socket = new Socket("127.0.0.1", BIOConstant.PORT);
            // 获取输出流
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            // 模拟 BIO 网络阻塞
            Thread.sleep(10 * 1000);
            // 输出数据 （server端按行读取，注意换行问题）
            printStream.println("hello world! --- 1 : " + LocalDateTime.now());
            // 清除历史数据
            printStream.flush();
            // 关闭连接
            socket.close();
            System.out.println("结束: " + LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
