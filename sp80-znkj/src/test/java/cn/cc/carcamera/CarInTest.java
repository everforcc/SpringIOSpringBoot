package cn.cc.carcamera;

import java.util.Base64;

/**
 * 车牌相机测试
 */
public class CarInTest {

    public static void main(String[] args) {
//        byte data[] = new byte[]{(byte) 0x00, (byte) 0x64, (byte) 0xFF, (byte) 0xFF, (byte) 0x0D, (byte) 0x01, (byte) 0x10, (byte) 0x26, (byte) 0xA2};
        // 00 64 FF FF 0D 01 0A 27 70
        // 00 64 FF FF 0D 01 64 A6 9C
        // 10
        byte data[] = new byte[]{
                (byte) 0x00,
                (byte) 0x64,
                (byte) 0xFF,
                (byte) 0xFF,
                (byte) 0x0D,
                (byte) 0x01,
                (byte) 0x0A,
                (byte) 0x27,
                (byte) 0x70
        };
        // 100
        byte data100[] = new byte[]{
                (byte) 0x00,
                (byte) 0x64,
                (byte) 0xFF,
                (byte) 0xFF,
                (byte) 0x0D,
                (byte) 0x01,
                (byte) 0x64,
                (byte) 0xA6,
                (byte) 0x9C
        };
        byte[] base64_data = Base64.getEncoder().encode(data);
        String base64_str = new String(base64_data);
        System.out.println(base64_str);
//        base64_str = "0064FFFF0D030A0A001D0C";
        System.out.println("{\"Response_AlarmInfoPlate\":{\"serialData\":[{\"serialChannel\":0,\"data\":\"" + base64_str + "\",\"dataLen\":" + data.length + "}]}}");
    }

}
