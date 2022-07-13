/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-13 12:27
 * Copyright
 */

package business.cmd.ffmpeg;

import cmd.ffmpeg.ConcatM3U8ToVideo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class ConcatM3U8ToVideoTests {

    // 文件名
    String fileName = "视频1.m3u8";
    // 本地根目录
    String path = "E:\\filesystem\\project\\SpringIOSpringBoot\\sp92-tools\\cmd\\ffmpeg\\m3u8\\" + fileName;

    // 原来的路径 可能不一样,从 m3u8文件内复制
    String oldPath_1 = "file:///storage/emulated/0/Quark/Download";
    String oldPath_2 = "file:///sdcard/Quark/Download";
    // String newPath = "E:\\\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8";

    /**
     * 分隔符测试
     */
    @Test
    public void fileSplit() {
        log.info(File.separator);
        File file = new File(path);
        String parent = file.getParent();
        log.info(parent);
        String name = file.getName();
        log.info(name);
        parent = parent.replace(File.separator, "/");
        log.info(parent);
        parent = parent.replaceFirst("/", "\\\\\\\\");
        log.info(parent);
    }

    /**
     * m3u8合并为视频
     */
    @Test
    public void concatFilePathToFile() {
        // 1. 处理m3u8文件生成新的
        String newM3U8FilePath = ConcatM3U8ToVideo.modifyM3u8Content(path, oldPath_2);
        // 2. 按照新的文件执行脚本
        ConcatM3U8ToVideo.m3u8ToMp4(newM3U8FilePath);
    }

}