package cn.cc.netty.learn.redis.socket;

import cn.cc.netty.learn.redis.constant.ConstantRESP;
import cn.cc.netty.learn.redis.constant.ConstantRedis;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * todo 1. 优化为字节流
 * todo 2. commandHash 分隔符自定义的为空格，导致 命令解析错误
 */
@Slf4j
public class RedisClientSocket {

    private static Socket socket = null;
    private static PrintWriter writer = null;
    private static BufferedReader reader = null;

    public static String[] importToCommand(String userImport) {
        return userImport.split(" ");
    }

    public static void connect() throws IOException {
        // 1. 建立连接
        socket = new Socket(ConstantRedis.HOST, ConstantRedis.PORT);
        // 2. 输入输出流
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        log.info("[连接成功]");
    }

    public static void flow(List<String> commandList) {
        commandList.forEach(command -> {
            RedisClientSocket.flow(command);
            log.info("-----------------");
        });
    }

    /**
     * 主流程
     */
    public static void flow(String command) {

        try {
            log.info("[发送命令] : {}", command);
            // 3. 发送命令
            sendCommand(writer, importToCommand(command));

            log.info("[发送命令成功]");
            // 4. 解析响应
            log.info("[开始读取]");
            Object result = readResponse(reader);
            log.info("[解析响应成功]: {}", result);

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
                log.info("[{}] : {}", ConstantRESP.SIMPLE_STRINGS, temp);
                return temp;
            case ConstantRESP.ERRORS:
                String err = reader.readLine();
                log.error("[{}] : {}", ConstantRESP.ERRORS, err);
                throw new RuntimeException(err);
            case ConstantRESP.INT:
                Long tempLong = Long.parseLong(reader.readLine());
                log.info("[{}] : {}", ConstantRESP.INT, tempLong);
                return tempLong;
            case ConstantRESP.BULK_STRINGS:
                int len = Integer.parseInt(reader.readLine());
                log.info("[{}] : {}", ConstantRESP.BULK_STRINGS, len);
                if (len == -1) {
                    return null;
                }
                // bug 如果返回的确实为空，需要读一下 CR 数据
                if (len == 0) {
                    return reader.readLine();
                }
                String line = reader.readLine();

                log.info("[{}] : {}", ConstantRESP.BULK_STRINGS, line);
                return line;
            case ConstantRESP.ARRAYS:
                return readBulkString(reader);
            default:
                log.error("[不支持的响应类型]: " + prefix + (char) (prefix));
                log.error("[不支持的响应类型]: " + reader.readLine());
                throw new RuntimeException("[不支持的响应类型]");
        }
    }

    private static Object readBulkString(BufferedReader reader) throws IOException {
        // 获取数组大小
        int len = Integer.parseInt(reader.readLine());

        log.info("[{}] : {}", ConstantRESP.ARRAYS, len);
        List<Object> list = new ArrayList<>(len);

        for (int i = 0; i < len; i++) {
            list.add(readResponse(reader));
        }
        return list;
    }


}
