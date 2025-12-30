/**
 * @Description
 * @Author everforcc
 * @Date 2022-12-14 10:02
 * Copyright
 */

package cn.cc.xiaoyu;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. 模拟充电桩发送请求
 */
@Slf4j
public class XiaoYuClientTest {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 流水线添加事件
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // TODO 连接建立好了以后触发
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
//                            for (int i = 0; i < 10; i++) {
                            ByteBuf byteBuf = ctx.alloc().buffer(37);
                            // byteBuf 写入十六进制 [0, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b]
                            byte b = 0;

                            byteBuf.writeBytes(new byte[]{0x0, (byte) 0xd5, 0x5d, 0x52, 0x0, 0x1, (byte) 0x81, 0x1, 0x0, 0x18, 0x38, 0x39, 0x38, 0x36, 0x30,
                                    0x34, 0x36, 0x31, 0x31, 0x36, 0x31, 0x39, 0x37, 0x32, 0x37, 0x37, 0x34, 0x32, 0x39, 0x30,
                                    0xb, 0x1b, 0x14, 0xf, 0x72, (byte) 0xed, (byte) 0xb3, 0x3b, (byte) 0xd5, 0x5d, 0x52, 0x0, 0x1,
                                    (byte) 0x81, 0x1, 0x0, 0x18, 0x38, 0x39, 0x38, 0x36, 0x30, 0x34, 0x36, 0x31, 0x31,
                                    0x36, 0x31, 0x39, 0x37, 0x32, 0x37, 0x37, 0x34, 0x32,
                                    0x39, 0x30, 0xb, 0x1b, 0x14, 0xf, 0x72, (byte) 0xed, (byte) 0xb3, 0x3b});
                            ctx.writeAndFlush(byteBuf);
                            System.out.println("等待后续操作...");
//                            }
                        }

                        // 接收服务器响应
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            // todo-文档响应 1.登录 2.XXX
                            // 接收服务器返回的数据
                            ByteBuf byteBuf = (ByteBuf) msg;
                            System.out.println("收到服务器响应: " + byteBufToHex(byteBuf));

                            // 处理响应数据
                            byte[] bytes = new byte[byteBuf.readableBytes()];
                            byteBuf.readBytes(bytes);

                            // 打印十六进制格式
                            System.out.println("响应数据长度: " + bytes.length);
                            System.out.println("响应数据(十六进制): " + bytesToHex(bytes));

                            // 释放ByteBuf资源
                            byteBuf.release();
                        }

                        // 连接异常处理
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                            System.out.println("连接异常: " + cause.getMessage());
                            ctx.close();
                        }

                        // 将ByteBuf转换为十六进制字符串
                        private String byteBufToHex(ByteBuf byteBuf) {
                            byte[] bytes = new byte[byteBuf.readableBytes()];
                            byteBuf.readBytes(bytes);
                            return bytesToHex(bytes);
                        }

                        // 将字节数组转换为十六进制字符串
                        private String bytesToHex(byte[] bytes) {
                            StringBuilder result = new StringBuilder();
                            for (byte b : bytes) {
                                result.append(String.format("%02x ", b));
                            }
                            return result.toString();
                        }


                    });
                }
            });

            // 链接服务器
            // https://dev-znyd.zgzhongnan.com/cc 80
            // 125.40.67.238 16999
//            ChannelFuture channelFuture = bootstrap.connect("192.168.1.188", 9999).sync();
//            ChannelFuture channelFuture = bootstrap.connect("125.40.67.238", 16999).sync();
            ChannelFuture channelFuture = bootstrap.connect("zzhx.zgzhongnan.com", 16999).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }


}
