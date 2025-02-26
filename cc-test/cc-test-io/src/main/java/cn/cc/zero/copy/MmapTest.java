package cn.cc.zero.copy;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MmapTest {

    public static void main(String[] args) {
        mmap();
    }

    public static void mmap(){
        File file = new File("D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\1.txt");
        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
