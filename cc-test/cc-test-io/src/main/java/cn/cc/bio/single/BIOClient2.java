package cn.cc.bio.single;

import cn.cc.bio.BIOConstant;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class BIOClient2 {

    public static void main(String[] args) {
        runClient();
    }

    private static void runClient() {

        Socket socket = null;
        try {
            System.out.println("开始: " + LocalDateTime.now());
            // 连接
            socket = new Socket("127.0.0.1", BIOConstant.PORT);
            // 获取输出流
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            // 输出数据 （server端按行读取，注意换行问题）
            printStream.println("hello world! --- 2 : " + LocalDateTime.now());
            // 清除历史数据
            printStream.flush();
            // 关闭连接
            socket.close();
            System.out.println("结束: " + LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
