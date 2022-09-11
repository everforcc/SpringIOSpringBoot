package cmd.compress.impl;

import cmd.compress.ISevenUtils;
import cmd.compress.constant.CompressConstant;
import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.runtime.CmdUtils;

import java.io.File;

public class SevenUtilsCMD implements ISevenUtils {

    /**
     * TODO 还没有对文件夹和文件名进行校验
     *
     * @param pas             文件密码
     * @param path            需要压缩的文件路径
     * @param fileTypeCMDEnum 压缩类型
     * @param compressEnum    压缩模式
     */
    @Override
    public void compress(String pas, String path, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path, path, path, pas, fileTypeCMDEnum, compressEnum);
    }

    /**
     * todo 限制文件名不能带.
     *
     * @param pas             压缩密码
     * @param path            旧文件/文件夹路径
     * @param newPath         新文件/文件夹路径
     * @param fileTypeCMDEnum 压缩类型 7z
     * @param compressEnum    压缩类型,文件还是目录
     */
    @Override
    public void compress(String pas, String path, String newPath, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path, path, newPath, pas, fileTypeCMDEnum, compressEnum);
    }

    /**
     * 解压
     *
     * @param pas     密码
     * @param oldPath 文件路径
     * @param newPath 解压后的路径
     */
    @Override
    public void deCompression(String pas, String oldPath, String newPath) {

        String cmd = String.format(CompressConstant.deCompressionCommand,
                oldPath,
                newPath,
                pas);
        System.out.println("cmd: " + cmd);
        String result = CmdUtils.execRuntime(cmd);
        System.out.println("执行结果: " + result);
    }

    /**
     * 递归处理文件
     *
     * @param path            旧文件目录跟地址
     * @param oldPath         旧文件地址
     * @param newPath         新文件地址
     * @param pas             密码
     * @param fileTypeCMDEnum 压缩文件类型 7z
     * @param compressEnum    压缩类型,文件还是目录
     */
    private static void recursionFile(String path, String oldPath, String newPath, String pas, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        File file = new File(path);
        // 获取该文件夹下的所有文件
        File[] fileList = file.listFiles();
        // 便利所有的文件夹
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                //dealFile(fileList[i]);
                //consumer.accept(fileList[i]);
                compressFile(fileList[i], oldPath, newPath, pas, fileTypeCMDEnum, compressEnum);
            }
        }
    }

    /**
     * 判断是文件还是文件夹
     *
     * @param file            文件
     * @param oldPath         旧路径
     * @param newPath         新路径
     * @param pas             密码
     * @param fileTypeCMDEnum 压缩类型 7z
     * @param compressEnum    压缩类型 文件还是文件夹
     */
    private static void compressFile(File file, String oldPath, String newPath, String pas, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        String absolutePath = file.getAbsolutePath();

        if (file.isDirectory()) {
            if (compressEnum.ISCD) {
                // 重复当前动作
                recursionFile(absolutePath, oldPath, newPath, pas, fileTypeCMDEnum, compressEnum);
            } else {
                String sourceFile = absolutePath + CompressConstant.all;
                //String targetFile = absolutePath + fileTypeCMDEnum.type;
                String targetFile = absolutePath.replace(oldPath, newPath);
                // TODO 执行压缩命令
                doCompress(pas, fileTypeCMDEnum.cmd_7z, targetFile, sourceFile);
            }
        } else {
            /*
             * 如果是每个文件都要逐级压缩，在就在最顶级生成一个文件夹
             *  /a/a/b
             *  例如7z
             *  /a/a/b7z
             */
            String sourceFile = absolutePath;
            if (compressEnum.ISCD) {
                absolutePath = absolutePath.replace(oldPath, newPath + fileTypeCMDEnum.toString());
            }
            String targetFile = absolutePath + fileTypeCMDEnum.type;
            // TODO 执行压缩命令
            doCompress(pas, fileTypeCMDEnum.cmd_7z, targetFile, sourceFile);
        }

    }

    /**
     * 组装压缩命令,调用压缩接口
     *
     * @param pas        密码
     * @param type_7z    压缩类型
     * @param targetFile 目标路径
     * @param sourceFile 源文件路径
     */
    private static void doCompress(String pas, String type_7z, String targetFile, String sourceFile) {

        /*targetFile = targetFile.replaceAll(" ", "\" \"").replaceAll("&","");
        sourceFile = sourceFile.replaceAll(" ", "\" \"").replaceAll("&","");*/

        /*targetFile = targetFile.replaceAll("&","");
        sourceFile = sourceFile.replaceAll("&","");*/

        // 组装密码
        if (RStringUtils.isNotEmpty(pas)) {
            pas = "-p" + pas;
        } else {
            pas = "";
        }
        // 组装命令
        String cmd = String.format(CompressConstant.compressionCommand,
                type_7z,
                targetFile,
                sourceFile,
                pas);

        //CmdUtils.execCmd(cmd);
        System.out.println("待执行cmd: " + cmd);
        // String result = CmdUtils.execProcess(cmd);
        String result = CmdUtils.execRuntime(cmd);
        System.out.println("执行结果 start:\r\n" + result);
        System.out.println("执行结果 end");
    }

}
