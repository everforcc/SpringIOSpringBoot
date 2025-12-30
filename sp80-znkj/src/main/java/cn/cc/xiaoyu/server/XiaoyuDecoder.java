package cn.cc.xiaoyu.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XiaoyuDecoder extends ByteToMessageDecoder {

    public static final byte HEADER_1 = (byte) 0xD5;
    public static final byte HEADER_2 = (byte) 0x5D;
    public static final byte TAIL_1 = (byte) 0xB3;
    public static final byte TAIL_2 = (byte) 0x3B;
    private static final  int dataLengthStart = 2 + 4 + 1 + 2;//数据包长度的起始

    private Integer start = null;

    private boolean checkCrc = true;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        printByteBuf(in);


        int startIndex = findHeader(in);
        log.info("发现起点:{}",startIndex);
        if (startIndex == -1){
            in.skipBytes(in.readableBytes()-1);
            in.discardReadBytes();
            return;
        }
        start = startIndex;

        //跳到首位
        in.readerIndex(start);
        in.markReaderIndex();



        //可读
        int readabled = in.readableBytes();
        log.info("可读字节:{},start:{}",readabled,start);
        if (readabled < dataLengthStart){
            return;
        }
        //忽略包头
        in.readShort();

        int deviceId = in.readInt();
        int cmd = in.readByte()&0xff;
        int dataLen = in.readUnsignedShort();


        //校验和的长度
        int crcLen = dataLengthStart + dataLen + 2 ;
        int allLen = crcLen + 2;

        if (readabled < allLen){
            return;
        }
        //读取设备核心信息
        byte [] data = new byte[dataLen];
        in.readBytes(data);

        //读取校验和
        int crc16 = in.readUnsignedShort();
        if (checkCrc){
            //校验crc16
            in.resetReaderIndex();
            byte[] b = new byte[dataLengthStart + dataLen];
            in.readBytes(b);

            crc16 = in.readUnsignedShort();
            int calculate = Crc16.calculate(b);

            if (calculate != crc16){
//                log.info("校验和错误,数组:{},结果:{},应该是:{}",XiaoyuUtil.hexString(b),calculate,crc16);
                in.discardReadBytes();
                return;
            }
        }


        byte t1 = in.readByte();
        byte t2 = in.readByte();
        if (t1 != TAIL_1 || t2 != TAIL_2){
            //丢弃数据
            log.info("包尾错误:{} {}",Integer.toHexString(t1 & 0xff),Integer.toHexString(t2 & 0xff));
            in.discardReadBytes();
            return;
        }
        in.discardReadBytes();
        out.add(new DataPacket(deviceId,cmd,data));

    }

    private void printByteBuf(ByteBuf in) {
        byte [] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        log.info("发来了数据:{}",XiaoyuUtil.hexString(bytes));
        in.resetReaderIndex();
    }



    private int findHeader(ByteBuf buffer) {
        for (int i = buffer.readerIndex(); i < buffer.writerIndex() - 1; i++) {
            byte b1 = buffer.getByte(i);
            byte b2 = buffer.getByte(i+1);
            if (b1 == HEADER_1 && b2 == HEADER_2) {
                return i;
            }
        }
        return -1;
    }


    public static class DataPacket {
        private int id;
        private int functionCode;
        private byte[] data;

        private ByteBuf byteBuf;

        private String idStr;

        public DataPacket(int id, int functionCode, byte[] data) {
            this.id = id;
            this.functionCode = functionCode;
            this.data = data;
            this.byteBuf = Unpooled.copiedBuffer(data);
            this.idStr = createIdStr(id);

        }

        private String createIdStr(int id){
            byte[] idArr = ByteBuffer.allocate(4).putInt(id).array();
//            List<String> list = XiaoyuUtil.hexString(idArr);
            List<String> list = new ArrayList<>();
            String idStr = "";
            for (String s : list) {
                if (s.length() == 1){
                    idStr += "0";
                }
                idStr += s;
            }
            return idStr;
            
        }


        public int getId() {
            return id;
        }

        public int getFunctionCode() {
            return functionCode;
        }

        public byte[] getData() {
            return data;
        }


        public ByteBuf getByteBuf() {
            return byteBuf;
        }

        public String getIdStr() {
            return idStr;
        }
    }


}
