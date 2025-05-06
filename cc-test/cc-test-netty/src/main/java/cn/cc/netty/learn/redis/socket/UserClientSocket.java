package cn.cc.netty.learn.redis.socket;

import cn.cc.netty.learn.redis.constant.ConstantRESP;
import cn.cc.netty.learn.redis.constant.ConstantRedis;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@Slf4j
public class UserClientSocket {

    public static void main(String[] args) {

        // 2. 获取 输出流 输入流
//        commandLine();
        userCommandTest();
    }

    public static void userCommandTest() {
        try {

            RedisClientSocket.connect();
            List<String> commandList = Arrays.asList(
                    "set name abc"
            );
            commandList.forEach(command -> {
                RedisClientSocket.flow(command);
                log.info("-----------------");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
