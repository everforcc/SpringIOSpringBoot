package cn.cc.xiaoyu.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoyuChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        XiaoyuDecoder.DataPacket dataPacket = (XiaoyuDecoder.DataPacket) msg;
        log.info("channelId:{},设备编号:{},指令:{},内容:{}", ctx.channel().id(), dataPacket.getIdStr(), dataPacket.getFunctionCode(), dataPacket.getData());
        if (1 == dataPacket.getFunctionCode()) {
            // todo 回复注册结果
            ByteBuf byteBuf = Unpooled.copiedBuffer(new byte[]{(byte) dataPacket.getFunctionCode()});
            ctx.channel().writeAndFlush(byteBuf);
//            ctx.writeAndFlush(new XiaoyuEncoder().encode(dataPacket.getId(),dataPacket.getFunctionCode(),new byte[]{0x01}));
        }
    }

}
