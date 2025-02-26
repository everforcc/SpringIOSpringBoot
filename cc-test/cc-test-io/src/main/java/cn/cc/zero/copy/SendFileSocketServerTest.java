package cn.cc.zero.copy;

import cn.cc.bio.BIOConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class SendFileSocketServerTest {

    public static void main(String[] args) {
        new SendFileSocketServerTest();
    }

    SendFileSocketServerTest() {
        socketChannelServer();
    }

    /**
     * 接收客户端的数据，并返回
     */
    public void socketChannelServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(BIOConstant.PORT);
            while (true) {
                System.out.println("等待监听端口: " + BIOConstant.PORT);
                socket = serverSocket.accept();
                System.out.println("开始: " + LocalDateTime.now());

                System.out.println("接收消息 开始: " + LocalDateTime.now());
                new ReceiveThread(socket).start();
                System.out.println("接收消息 结束: " + LocalDateTime.now());

                System.out.println("发送消息 开始: " + LocalDateTime.now());
                new SendThread(socket, "cccccc").start();
                System.out.println("发送消息 结束: " + LocalDateTime.now());

                System.out.println("结束: " + LocalDateTime.now());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息的线程
     */
    class SendThread extends Thread {
        Socket socket;
        String str;

        public SendThread(Socket socket, String str) {
            this.socket = socket;
            this.str = str;
        }

        @Override
        public void run() {
            super.run();
            try {
                socket.getOutputStream().write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接受消息的线程
     */
    class ReceiveThread extends Thread {
        //        int num;
        Socket socket;// 客户端对应的套接字
        boolean continueReceive = true;// 标识是否还维持连接需要接收

        public ReceiveThread(Socket socket) {
            this.socket = socket;
//            this.num = num;
            try {
                // 给连接上的客户端发送，分配的客户端编号的通知
                socket.getOutputStream().write(("aaaaa").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();
            // 接收客户端发送的消息
            InputStream inputStream = null;
            try {
                inputStream = socket.getInputStream();
                byte[] b = new byte[6];
                while (continueReceive) {
                    inputStream.read(b);
                    // b = splitByte(b);// 去掉数组无用部分
                    // 发送end的客户端断开连接
                    System.out.println(new String(b));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {// 关闭资源
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
