package cn.cc.zero.copy;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class SendFileSocketTest {

    public static void main(String[] args) {
//        sendFileWEB();
        sendFileWEB2();
    }

    /**
     * 已合并到下面的方法
     */
    public static void sendFileWEB() {
        try {
            String sourceFile = "D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\socket.txt";
            FileChannel sourceChannel = new RandomAccessFile(sourceFile, "rw").getChannel();
            SocketChannel socketChannel = null;
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
            sourceChannel.transferTo(0, sourceChannel.size(), socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从文件读取数据，直接发送到web
     */
    public static void sendFileWEB2() {
        try {
            boolean type = true;

            String sourceFile = "D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\socket.txt";
            FileChannel sourceChannel = new RandomAccessFile(sourceFile, "rw").getChannel();

            // 打开 SocketChannel
            SocketChannel socketChannel = SocketChannel.open();

            // 设置为非阻塞模式
            socketChannel.configureBlocking(false);

            // 连接到服务器
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

            // 等待连接完成
            while (!socketChannel.finishConnect()) {
                // 可以在这里做其他事情，不阻塞
            }
            if (type) {
                // 从文件读取，写入到socket
                sourceChannel.transferTo(0, sourceChannel.size(), socketChannel);
            } else {
                // 发送数据
                String message = "1 abcd";
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
            }

            // 接收数据
            ByteBuffer readBuffer = ByteBuffer.allocate(6);

            // 控制台打印
            int bytesRead = socketChannel.read(readBuffer);
            while (bytesRead != -1) {
                readBuffer.flip();
//                String result = new String(readBuffer.array(), StandardCharsets.UTF_8);
//                System.out.println(result);
                while (readBuffer.hasRemaining()) {
                    System.out.print((char) readBuffer.get());
                }
                readBuffer.clear();
                bytesRead = socketChannel.read(readBuffer);
            }
//            }
            // 关闭 SocketChannel
            socketChannel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
