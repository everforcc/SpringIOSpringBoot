package cn.cc.netty.learn.redis.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 1. 解析Redis协议，将字节数组转为Command对象。
 *
 * todo ReplayingDecoder
 */
public class RedisCommandDecoder extends ReplayingDecoder<Void> {

    /** Decoded command and arguments */
    private byte[][] cmds;

    /** Current argument */
    private int arg;

    /** Decode in block-io style, rather than nio. */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("执行了一次方法");
        if (cmds == null) {
            if (in.readByte() == '*') {
                // 先取出来的命令数量
                doDecodeNumOfArgs(in);
            }
        } else {
            // 解析具体的命令参数
            doDecodeArgs(in);
        }

        if (isComplete()) {
            // 设置到参数里面
            doSendCmdToHandler(out);
            doCleanUp();
        }
    }

    /** Decode number of arguments */
    private void doDecodeNumOfArgs(ByteBuf in) {
        // Ignore negative case
        int numOfArgs = readInt(in);
        System.out.println("RedisCommandDecoder NumOfArgs: " + numOfArgs);
        cmds = new byte[numOfArgs][];

        // todo 看看这段解析
        checkpoint();
    }

    /** Decode arguments */
    private void doDecodeArgs(ByteBuf in) {
        for (int i = arg; i < cmds.length; i++) {
            if (in.readByte() == '$') {
                int lenOfBulkStr = readInt(in);
                System.out.println("RedisCommandDecoder LenOfBulkStr[" + i + "]: " + lenOfBulkStr);

                cmds[i] = new byte[lenOfBulkStr];
                in.readBytes(cmds[i]);

                // Skip CRLF(\r\n)
                in.skipBytes(2);

                arg++;
                checkpoint();
            } else {
                throw new IllegalStateException("Invalid argument");
            }
        }
    }

    /**
     * cmds != null means header decode complete
     * arg > 0 means arguments decode has begun
     * arg == cmds.length means complete!
     */
    private boolean isComplete() {
        return (cmds != null)
                && (arg > 0)
                && (arg == cmds.length);
    }

    /** Send decoded command to next handler */
    private void doSendCmdToHandler(List<Object> out) {
        out.add(new RedisCommand("test"));
        System.out.println("RedisCommandDecoder: Send command to next handler");
        if (cmds.length == 1) {
            out.add(new RedisCommand(new String(cmds[0])));
        } else if (cmds.length == 2) {
            out.add(new RedisCommand(new String(cmds[0]), cmds[1]));
        } else if (cmds.length == 3) {
            out.add(new RedisCommand(new String(cmds[0]), cmds[1], cmds[2]));
        } else {
            throw new IllegalStateException("Unknown command");
        }
    }

    /** Clean up state info */
    private void doCleanUp() {
        this.cmds = null;
        this.arg = 0;
    }

    private int readInt(ByteBuf in) {
        // 假设输入的是字符串 "123\r\n"
        int integer = 0;
        char c;
        // c: 是从输入缓冲区读取的一个字符（如 '1', '2', '3' 等）。
        // '0': 是字符 '0' 的 ASCII 值（即 48），用于将字符数字转为真正的整数值。
        // c - '0': 将字符 '0' 到 '9' 转换为整数 0 到 9。
        while ((c = (char) in.readByte()) != '\r') {
            // 实现多位数字的拼接。比如：
            integer = (integer * 10) + (c - '0');
        }

        if (in.readByte() != '\n') {
            throw new IllegalStateException("Invalid number");
        }
        return integer;
    }

}