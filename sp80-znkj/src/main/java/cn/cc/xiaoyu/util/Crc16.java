package cn.cc.xiaoyu.util;

import java.util.ArrayList;
import java.util.List;

public class Crc16 {
    private List<Byte> bytes = new ArrayList<>();
    private static final int POLYNOMIAL = 0x1021;
    private static final int INITIAL_VALUE = 0xFFFF;

    public static int calculate(byte[] data) {
        return calculate(data, data.length);
    }

    public static int calculate(byte[] data, int len) {
        int CRC = 0x0000ffff;
        //多项式校验值
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < len; i++) {
            CRC ^= ((int) data[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }

        return CRC;
    }


    public static void main(String[] args) {

        byte[] data = new byte[]{(byte) 0xd5, (byte) 0x5d,
                0x52, 0x0, 0x1, (byte) 0x81,
                0x2,
                0x0, 0x18,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,0x0,0x0,0x0,0x0,
                };


        int crc = calculate(data);
        System.out.printf("CRC16: %04X", crc);
    }
}
