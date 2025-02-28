package nio.file.files;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class FilesTest {

    @Test
    public void lines() {
        try {
            // 读取文件内容
            String path = "D:\\cache\\BaiduSyncdisk\\java\\project\\SpringIOSpringBoot\\cc-test\\cc-test-file\\1.txt";
            Stream<String> stringStream = Files.lines(Paths.get(path));

            // 打印文件
//            stringStream.forEach(System.out::println);

            // 流被消耗一次就没了
            // 读取文件行数
            long count = stringStream.count();
            log.info("行数: {}", count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
