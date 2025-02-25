package cn.cc.bio.single;

import cn.cc.bio.BIOConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class BIOServer {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(BIOConstant.PORT);
            while (true) {
                // 监听端口上的socket请求
                // 此处会暂定等待客户端的socket
                // accept 一次，只会接受一个socket
                System.out.println("等待监听端口: " + BIOConstant.PORT);
                socket = serverSocket.accept();
                System.out.println("开始: " + LocalDateTime.now());
                // 获取输入流
                InputStream inputStream = socket.getInputStream();
                // 获取数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("结束: " + LocalDateTime.now());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
