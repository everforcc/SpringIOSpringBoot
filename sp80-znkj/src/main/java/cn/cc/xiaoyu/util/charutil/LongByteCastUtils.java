package cn.cc.xiaoyu.util.charutil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 处理订单号传输转换问题
 */
public class LongByteCastUtils {

    public static void main(String[] args) {
        // 测试用的 long 数值
        long originalLong = 1234567890123456789L;
        System.out.println("原始 long 值：" + originalLong);

        // ========== long 转 byte[]（大端序，默认） ==========
        byte[] byteArray = longToBytes(originalLong);
        System.out.print("long 转 byte[]（大端序）：");
        printByteArray(byteArray);

        // ========== byte[] 转 long（大端序，默认） ==========
        long convertedLong = bytesToLong(byteArray);
        System.out.println("byte[] 转回 long 值：" + convertedLong);

        // ========== 可选：小端序转换（如需适配特定系统/硬件） ==========
        byte[] byteArrayLittle = longToBytes(originalLong, ByteOrder.LITTLE_ENDIAN);
        System.out.print("long 转 byte[]（小端序）：");
        printByteArray(byteArrayLittle);
        long convertedLongLittle = bytesToLong(byteArrayLittle, ByteOrder.LITTLE_ENDIAN);
        System.out.println("小端序 byte[] 转回 long 值：" + convertedLongLittle);
    }

    /**
     * long 转 byte[]（默认大端序）
     */
    public static byte[] longToBytes(long value) {
        return longToBytes(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * long 转 byte[]（指定端序）
     */
    public static byte[] longToBytes(long value, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.allocate(8); // long 占8字节
        buffer.order(order); // 设置端序
        buffer.putLong(value);
        return buffer.array();
    }

    /**
     * byte[] 转 long（默认大端序）
     */
    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
    }

    /**
     * byte[] 转 long（指定端序）
     * 注意：bytes 长度必须为8，否则会抛异常
     */
    public static long bytesToLong(byte[] bytes, ByteOrder order) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException("byte数组长度必须为8（long占8字节）");
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(order);
        return buffer.getLong();
    }

    /**
     * 辅助方法：打印 byte 数组（十六进制形式，方便查看）
     */
    private static void printByteArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b)); // 两位十六进制，补0
        }
        System.out.println(sb.toString().trim());
    }

}
