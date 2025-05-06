package cn.cc.netty.learn.redis;

import cn.cc.netty.learn.redis.socket.RedisClientSocket;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class UserClientSocketTest {

    public static void main(String[] args) {
        commandLine();
    }

    /**
     * 测试用户录入命令行
     */
    public static void commandLine() {
        try {
            RedisClientSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                // 获取系统用户录入命令
                log.info("等待用户录入");
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();
                RedisClientSocket.flow(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
