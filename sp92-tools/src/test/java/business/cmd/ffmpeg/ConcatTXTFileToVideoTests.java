/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-13 09:54
 * Copyright
 */

package business.cmd.ffmpeg;

import cmd.ffmpeg.ConcatTXTFileToVideo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ConcatTXTFileToVideoTests {

    @Test
    public void logT() {
        log.info("log-info");
    }

    /**
     * 1. 合并文件清单
     */
    @Test
    public void concatFilePathToFile() {
        String path = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\cmd\\ffmpeg\\concatfile\\104e476d2be01cefa4a08d7728ac6487";
        String pre = "";
        String resultFileName = ConcatTXTFileToVideo.concatFilePathToFile(path);
    }

    /**
     * 2. 根据文件清单,合并视频
     */
    @Test
    public void exec() {
        String resultFileName = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\cmd\\ffmpeg\\concatfile\\104e476d2be01cefa4a08d7728ac6487.txt";
        ConcatTXTFileToVideo.exec(resultFileName);
    }

    /**
     * 完整流程
     */
    @Test
    public void flow() {
        String path = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\cmd\\ffmpeg\\concatfile\\2569c9abc8eb570b698d7f6b0a25d171";
        String resultFileName = ConcatTXTFileToVideo.concatFilePathToFile(path);
        ConcatTXTFileToVideo.exec(resultFileName);
    }

}
