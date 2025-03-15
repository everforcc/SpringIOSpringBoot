package cn.cc.nio.tencent.article2380251;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @see <a href="https://cloud.tencent.com/developer/article/2380251">原文地址</a>
 */
public class NIOServer {

    private Selector selector;

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.startServer();
    }

    public void startServer() throws IOException {
        // 创建Selector
        selector = Selector.open();

        // 创建ServerSocketChannel，并绑定端口
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(8888));

        // 将ServerSocketChannel注册到Selector上，并监听连接事件。当接收到一个客户端连接请求时就绪。该操作只给服务器使用。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port 8888");

        // 循环等待事件发生
        while (true) {

            // 等待事件触发，阻塞 ｜ selectNow():非阻塞，立刻返回。
            // 监听，等任意一个或多个socket有数据了就返回给select()，然后再去调用对应的方法获得数据
            selector.select();

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                // 移除当前处理的SelectionKey
                keys.remove();

                if (key.isAcceptable()) {
                    // 处理连接请求
                    handleAccept(key);
                }

                if (key.isReadable()) {
                    // 处理读数据请求
                    handleRead(key);
                }
            }
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        // 监听到ServerSocketChannel连接事件，获取到连接的客户端
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        // 将clientChannel注册到Selector上，并监听读事件，当操作系统读缓冲区有数据可读时就绪（该客户端的）。
        clientChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("Client connected: " + clientChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            // 客户端断开连接
            key.cancel();
            clientChannel.close();
            System.out.println("[" + clientChannel.getRemoteAddress() + "] --- Client disconnected ");
            return;
        }

        byte[] data = new byte[bytesRead];
        buffer.flip();
        buffer.get(data);

        String message = new String(data).trim();
        System.out.println("[" + clientChannel.getRemoteAddress() + "] --- Received message from client: " + message);

        // 回复客户端
        String response = "Server response: " + message;
        ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientChannel.write(responseBuffer);
    }
}