/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 19:49
 * Copyright
 */

package algo.size;

import com.cc.sp90utils.commons.io.RFileUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * 计算下内存和数据的关系
 */
public class SysSize {

    private static final String[] stringAry = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 数组长度
     */
    private static final int arySize = stringAry.length;

    /**
     * size单位
     */
    private static final long unit = 1 << 10;

    /**
     * 文件路径
     */
    private static final String filePath = "E:/临时/algo/filesize/";
    /**
     * 文件名
     */
    private static final String fileNameFormat = "%s%s-%s.txt";

    /**
     * 随机生成一个字符
     *
     * @return 字符
     */
    private static String generalStr() {
        // 生成字符串到文件,计算大小
        int random = RandomUtils.nextInt(0, arySize - 1);
        return stringAry[random];
    }

    /**
     * 随机生成n个字符串
     *
     * @param length 字符串长度
     * @return 字符串
     */
    public static synchronized String randomString(long length) {
        if (length < 0) {
            throw new RuntimeException("长度必须大于0");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= length; i++) {
            stringBuilder.append(generalStr());
            if (i % 9 == 0) {
                stringBuilder.append("\n");
                i++;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 生成指定大小的文件
     * 一个字符占用一个字节
     */
    public static void generalFileBySize(long kb) {
        long strSize = kb * unit;
        long timeStamp = System.currentTimeMillis();
        String fileName = String.format(fileNameFormat, filePath, timeStamp, kb);
        String content = randomString(strSize);
        RFileUtils.writeStringToFile(fileName, content);
        try {
            System.out.println("开始: " + kb);
            //Thread.sleep(1000 * 10);
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            //Thread.sleep(1000 * 10);
            //System.out.println(21 / 10);
            //System.out.println(stringAry.length);

//            System.out.println("等待 jconsole 连接 ...");
//            for (int i = 1; i <= 10; i++) {
//                long mb = i * unit;
//                generalFileBySize(mb);
//            }
            String result = randomString(1 * unit * unit);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (true) {
                System.out.println("---: " + i++);
                stringBuilder.append(result);
                System.out.println("length: " + stringBuilder.length());
                Thread.sleep(1000 * 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
