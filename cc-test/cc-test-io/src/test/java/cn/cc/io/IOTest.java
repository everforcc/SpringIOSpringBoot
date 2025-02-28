package cn.cc.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;


@Slf4j
public class IOTest {

    private static final String filePath = "D:/cache/BaiduSyncdisk/java/project/SpringIOSpringBoot/cc-test/cc-test-file/1.txt";

    /**
     * 同步IO
     * Synchronous IO
     * <p>
     * 阻塞IO
     * Blocking IO
     */
    @Test
    public void bio() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            int data = fis.read(); // 阻塞直到有数据可读
            System.out.println(data);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步io
     */
    @Test
    public void aio() {
        for (int i = 0; i < 10; i++) {
            Path path = Paths.get(filePath);
            AsynchronousFileChannel fileChannel = null;
            try {
                fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fileChannel.read(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("Read {} bytes", result);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.info("Read failed");
                }
            });
            // 主线程可以继续执行其他任务
            log.info("后续任务: {}", i);
        }
    }

    /**
     * 非阻塞io（Non-blocking IO）
     */
    @Test
    public void nio(){
        try {
            // 可以使用轮询或者
            // selector 选择器处理
            Selector selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
