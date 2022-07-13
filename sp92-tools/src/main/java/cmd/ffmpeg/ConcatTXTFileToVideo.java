/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-13 09:26
 * Copyright
 */

package cmd.ffmpeg;

import cmd.ffmpeg.constant.ConstantFFmpeg;
import com.cc.sp90utils.commons.io.JInputStreamCharUtils;
import com.cc.sp90utils.exception.AppCode;
import com.cc.sp90utils.runtime.CmdUtils;

import java.io.File;
import java.util.Objects;

/**
 * 将多个文件片段 放入文件清单
 * D:/ABC/download/pre0
 * D:/ABC/download/pre1
 * <p>
 * 文件名 D:/ABC/download.txt
 * 文件内容
 * file 'D:/ABC/download/pre0'
 * file 'D:/ABC/download/pre1'
 * <p>
 * ffmpeg命令合并为
 * 文件名 D:/ABC/download.txt.mp4
 */
public class ConcatTXTFileToVideo {

    /**
     * 默认空和o的方法
     *
     * @param path 文件路径
     * @return 生成后的文件名
     */
    public static String concatFilePathToFile(String path) {
        return concatFilePathToFile(path, "", 0);
    }

    /**
     * 传入文件夹路径 D:/ABC/download
     * 生成的文件为  D:/ABC/download.txt
     * 文件夹内数据格式为
     * /pre0
     * /pre1
     * /pre10086
     * <p>
     * 默认编号从0开始
     *
     * @param path 文件夹路径
     * @param pre  文件夹内文件的前缀
     */
    public static String concatFilePathToFile(String path, String pre, int startNo) {
        // 生成的文件名直接在目录后加.txt
        String resultFileName = path + ".txt";

        JInputStreamCharUtils jInputStreamCharUtils = new JInputStreamCharUtils(resultFileName);

        File fileDir = new File(path);
        if (!fileDir.isDirectory()) {
            throw AppCode.A00100.toUserException("必须是文件夹才能转换");
        }

        int length = Objects.requireNonNull(fileDir.listFiles()).length;
        for (int i = startNo; i < length; i++) {
            // 文件内容格式
            // file 'D:/ABC/download/pre0'
            String absolutePath = path + File.separator + pre + i;
            // 如果文件存在就追加,如果文件不存在就放弃
            if (new File(absolutePath).exists()) {
                String str = "file '" + absolutePath + "'";
                // 生成与文件夹同名的txt，里面包含目录下的所有文件路径信息
                jInputStreamCharUtils.printWriterAppend(str);
            }
        }
        return resultFileName;
    }

    /**
     * 执行命令
     *
     * @param fileListPath 文件清单路径
     *                     newFilePath  生成文件路径,在源文件后面追加.mp4
     *                     原文件为  D:/ABC/download.txt
     *                     生成的文件为  D:/ABC/download.txt.mp4
     */
    public static void exec(String fileListPath) {
        String COMMAND = String.format(ConstantFFmpeg.txtFileListCOMMAND, fileListPath, fileListPath + ".mp4");
        CmdUtils.execProcess(COMMAND);
    }

}
