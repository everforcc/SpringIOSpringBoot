/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-13 11:39
 * Copyright
 */

package cmd.ffmpeg;


import cmd.ffmpeg.constant.ConstantFFmpeg;
import cn.cc.utils.commons.io.JInputStreamCharUtils;
import cn.cc.utils.commons.lang.RStringUtils;
import cn.cc.utils.exception.AppCode;
import cn.cc.utils.runtime.CmdUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ConcatM3U8ToVideo {

    /**
     * 整体流程
     * 1. 修改m3u8文件内容
     * --   将 host/path 修改为本地路径
     * 2. 执行合并命令
     */
    /**
     * file:///sdcard/Quark/Download/name_3919545194.m3u8_contents/0
     *
     * @param path    m3u8 文件所在路径         E:\filesystem\project\SpringIOSpringBoot\sp92-tools\cmd\ffmpeg\m3u8\视频.m3u8
     * @param oldPath 文件内的旧路径 示例        "file:///storage/emulated/0/Quark/Download"
     *                newPath 文件内的新路径 示例        "E:\\\\filesystem/project/SpringIOSpringBoot/sp92-tools/cmd/ffmpeg/m3u8"
     */
    public static String modifyM3u8Content(String path, String oldPath) {//, String newPath
        if (RStringUtils.isEmpty(path)) {
            throw AppCode.A00100.toUserException("路径不能为空");
        }
        if (!path.endsWith(".m3u8")) {
            throw AppCode.A00100.toUserException("必须是m3u8文件");
        }
        File m3u8File = new File(path);
        String parentPath = m3u8File.getParent();
        String fileName = m3u8File.getName();

        // 生成新的m3u8文件
        String newFileName = "new-" + fileName;
        String newM3U8File = parentPath + File.separator + newFileName;
        JInputStreamCharUtils jInputStreamCharUtils = new JInputStreamCharUtils(newM3U8File);
        // 默认字符编码GBK
        try {
            // 每次读一行
            String readLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(m3u8File)));
            while ((readLine = br.readLine()) != null) {
                // #开头的是注释不用管,非#开头的需要转换
                // 注释内可能有 key还是处理下
//                if (readLine.startsWith("#")) {
//                    log.info("注释: 【{}】 ", readLine);
//                    jInputStreamCharUtils.printWriterAppend(readLine);
//                } else {
                log.info("path:" + path);
                log.info("fileName:" + fileName);
                log.info("readLine:" + readLine);
                String result = parsePath(readLine, oldPath, parentPath);
                jInputStreamCharUtils.printWriterAppend(result);
//                }
            }
            // 命令处理为mp4

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newM3U8File;
    }

    /**
     * 处理 m3u8的内容
     *
     * @param txt     m3u8读取一行
     * @param oldPath 旧路径
     * @param newPath 新路径
     * @return 处理后的信息
     */
    private static String parsePath(String txt, String oldPath, String newPath) {

        // 1. 将所有分隔符替换为 /
        newPath = newPath.replace(File.separator, "/");
        // 2. 将所有 / 替换为 \\
        newPath = newPath.replaceFirst("/", "\\\\\\\\");
        // 3. 将文件 从历史路径替换为额新路径
        txt = txt.replace(oldPath, newPath);

        // windows字符限制 | 替换为 _
        //txt = txt.replaceAll("\\|", "_");

        return txt;
    }

    /**
     * 用生成的新m3u8文件执行命令
     * 生成mp4视频
     * <p>
     * 例如 E:/path/new-video.m3u8
     * 那么生成的视频为 E:/path/new-video.m3u8.mp4
     *
     * @param newM3U8FilePath 新m3u8文件路径
     */
    public static void m3u8ToMp4(String newM3U8FilePath) {

        if (newM3U8FilePath == null) {
            return;
        }
        // 视频路径
        String videoPath = newM3U8FilePath + ".mp4";
        // 格式化命令
        String COMMAND = String.format(ConstantFFmpeg.m3u8ToVideoCOMMAND, newM3U8FilePath, videoPath);
        // 执行命令
        CmdUtils.execProcess(COMMAND);
        // 命令行返回输出完了，才算结束
        log.info("cmd >>> 合并结束");
    }

}
