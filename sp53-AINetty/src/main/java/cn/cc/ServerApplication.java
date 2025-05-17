package cn.cc;

import cn.cc.netty.NettyServer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerApplication {
    public static void main(String[] args) {
        System.out.println("服务端启动...");
        int port = 8080;
        try (InputStream in = ServerApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            if (in != null) {
                properties.load(in);
                port = Integer.parseInt(properties.getProperty("netty.port", "8080").trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new NettyServer(port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 