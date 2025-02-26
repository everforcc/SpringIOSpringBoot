package cn.cc.zero.copy;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class SendFileIOTest {

    public static void main(String[] args) {
        sendFileIO();
    }

    public static void sendFileIO() {
        String srcPath = "D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\1.txt";
        String targetPath = "D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\2.txt";

        try (
                RandomAccessFile srcRandomAccessFile = new RandomAccessFile(srcPath, "rw");
                FileChannel srcChannel = srcRandomAccessFile.getChannel();
                RandomAccessFile targetRandomAccessFile = new RandomAccessFile(targetPath, "rw");
                FileChannel targetChannel = targetRandomAccessFile.getChannel()) {
            //
            srcChannel.transferTo(0, srcChannel.size(), targetChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 这里不能通过文件输出流去获取通道，因为获取到的通道是只读的
//        File src = new File(srcPath);
//        File target = new File(targetPath);
//        try (FileChannel srcChannel = new FileInputStream(src).getChannel();
//             FileChannel targetChannel = new FileInputStream(target).getChannel()) {
//            srcChannel.transferTo(0, srcChannel.size(), targetChannel );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}
