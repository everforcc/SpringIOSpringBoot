package cn.cc.nio.tencent.article2380251;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClient {
    private Selector selector;
    private SocketChannel socketChannel;

    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        new Thread(() -> client.doConnect("localhost", 8888)).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            if ("bye".equals(message)) {
                // 如果发送的消息是"bye"，则关闭连接并退出循环
                client.doDisConnect();
                break;
            }
            client.sendMsg(message);
        }

    }

    private void doDisConnect() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String message) {
        // 发送消息到服务器
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        try {
            socketChannel.write(buffer);
            if("1".equals(message)){
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doConnect(String host, int port) {
        try {
            selector = Selector.open();
            // 创建SocketChannel并连接服务器
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            // 等待连接完成
            while (!socketChannel.finishConnect()) {
                // 连接未完成，可以做一些其他的事情
            }
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("连接成功！");
            while (true) {
                // 等待事件触发，阻塞 ｜ selectNow():非阻塞，立刻返回。
                selector.select();

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    // 移除当前处理的SelectionKey
                    keys.remove();
                    if (key.isReadable()) {
                        // 处理读数据请求
                        handleRead(key);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("连接失败！！！");
            e.printStackTrace();
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            // 释放资源
            key.cancel();
            clientChannel.close();
            return;
        }

        byte[] data = new byte[bytesRead];
        buffer.flip();
        buffer.get(data);

        String message = new String(data).trim();
        System.out.println("Received message from server: " + message);
    }


}