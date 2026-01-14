package cn.cc.xiaoyu.client.handler;

import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import cn.cc.xiaoyu.util.charutil.AsciiUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * 定时向服务器发送心跳包
 */
@Slf4j
public class XiaoYuHeartHandler extends ChannelDuplexHandler {

    private IXiaoYuClient iXiaoYuClient;

    public XiaoYuHeartHandler() {
    }

    public XiaoYuHeartHandler(IXiaoYuClient iXiaoYuClient) {
        this.iXiaoYuClient = iXiaoYuClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        // 触发了写空闲事件
        if (event.state() == IdleState.WRITER_IDLE) {
            log.debug("3s 没有写数据了，发送一个心跳包: {}", AsciiUtils.hexString(iXiaoYuClient.getHeartData()));
            ByteBuf byteBuf = ctx.alloc().buffer(37);
            // byteBuf 写入十六进制 [0, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b]
//                                        byteBuf.writeBytes(new byte[]{
//                                                (byte) 0xd5, (byte) 0x5d,
//                                                0x52, 0x0, 0x1, (byte) 0x81,
//                                                0x2,
//                                                0x0, 0x14,
//                                                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
//                                                0x4f, (byte) 0xfa,
//                                                (byte) 0xb3, 0x3b});
            /**
             * [d5, 5d,
             * 52, 0, 1, 81,
             * 2,
             * 0, 14,
             * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
             */
            byteBuf.writeBytes(iXiaoYuClient.getHeartData());
            ctx.writeAndFlush(byteBuf);


            // 方式1：使用位运算组合
//            int cardValue = ((card_1 & 0xFF) << 24) |
//                    ((card_2 & 0xFF) << 16) |
//                    ((card_3 & 0xFF) << 8) |
//                    (card_4 & 0xFF);

            // 方式2：使用ByteBuffer
            int id = ByteBuffer.wrap(iXiaoYuClient.getId()).getInt();

            log.info("3s 没有写数据了，当前通道信息id: {} 步骤: {}",id, XiaoYuClientHandler.step7Map.get(id));


        }
    }

}
