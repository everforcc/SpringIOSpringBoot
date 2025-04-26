package cn.cc.netty.learn.redis.socket;

import cn.cc.netty.learn.redis.constant.ConstantRESP;
import cn.cc.netty.learn.redis.constant.ConstantRedis;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RedisClientSocket {

    public static void main(String[] args) {

        // 2. 获取 输出流 输入流
        //
        flow();
    }

    /**
     * 主流程
     */
    public static void flow() {
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            // 1. 建立连接
            socket = new Socket(ConstantRedis.HOST, ConstantRedis.PORT);

            // 2. 输入输出流
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            System.out.println("连接成功");
            // 3. 发送命令
            //sendCommand(writer, "LRANGE", "list", "0", "3");
            sendCommand(writer, "LPUSH", "runoobkey ", "redis");
            System.out.println("发送命令成功");
            // 4. 解析响应
            System.out.println("开始读取");
            Object result = readResponse(reader);
            System.out.println("result: " + result);
            System.out.println("解析响应成功");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送一个命令
     * 数组
     * *3
     * $3
     * SET
     * $3
     * key
     * $5
     * value
     */
    private static void sendCommand(PrintWriter writer, String... command) {
        // writer.write
        writer.println(ConstantRESP.ARRAYS_STR + command.length);
        for (String str : command) {
            // 这里要去字节长度
            writer.println(ConstantRESP.BULK_STRINGS_STR + str.getBytes(StandardCharsets.UTF_8).length);
            writer.println(str);
        }
        writer.flush();
    }

    /**
     * 应该用字节流处理，但是比较麻烦，先看协议，用字符流处理
     */
    private static Object readResponse(BufferedReader reader) throws IOException {
        int prefix = reader.read();

        switch (prefix) {
            case ConstantRESP.SIMPLE_STRINGS:
                String temp = reader.readLine();
                System.out.println(ConstantRESP.SIMPLE_STRINGS + ": " + temp);
                return temp;
            case ConstantRESP.ERRORS:
                String err = reader.readLine();
                System.err.println(ConstantRESP.ERRORS + ": " + err);
                throw new RuntimeException(err);
            case ConstantRESP.INT:
                Long tempLong = Long.parseLong(reader.readLine());
                System.out.println(ConstantRESP.INT + ": " + tempLong);
                return tempLong;
            case ConstantRESP.BULK_STRINGS:
                int len = Integer.parseInt(reader.readLine());
                System.out.println(ConstantRESP.BULK_STRINGS + ": " + len);
                if (len == -1) {
                    return null;
                }
                // bug 如果返回的确实为空，需要读一下 CR 数据
                if (len == 0) {
                    return reader.readLine();
                }
                String line = reader.readLine();

                System.out.println(ConstantRESP.BULK_STRINGS + ":  " + line);
                return line;
            case ConstantRESP.ARRAYS:
                return readBulkString(reader);
            default:
                System.err.println("不支持的响应类型: " + prefix + (char)(prefix));
                System.err.println("不支持的响应类型: " + reader.readLine());
                throw new RuntimeException("不支持的响应类型");
        }
    }

    private static Object readBulkString(BufferedReader reader) throws IOException {
        // 获取数组大小
        int len = Integer.parseInt(reader.readLine());

        System.out.println(ConstantRESP.ARRAYS + ": " + len);
        List<Object> list = new ArrayList<>(len);

        for (int i = 0; i < len; i++) {
            list.add(readResponse(reader));
        }
        return list;
    }


}
