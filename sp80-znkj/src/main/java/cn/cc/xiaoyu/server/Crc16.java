package cn.cc.xiaoyu.server;

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
        String s = "e5,5e,31,0,3,44,1,0,17,3,38,39,38,36,30,34,38,32,31,31,32,30,37,30,31,34,32,35,39,36,14,0";
        String[] split = s.split(",");
        byte[] data = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            data[i] = (byte) Integer.parseInt(split[i], 16);
        }

        int crc = calculate(data);
        System.out.printf("CRC16: %04X", crc);
    }
}
