package cmd.compress.impl;

import cmd.compress.constant.CompressConstant;
import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import cmd.compress.ISevenUtils;
import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.runtime.CmdUtils;
import java.io.File;

public class SevenUtilsCMD implements ISevenUtils {

    /**
     * TODO 还没有对文件夹和文件名进行校验
     * @param pas 文件密码
     * @param path 需要压缩的文件路径
     * @param fileTypeCMDEnum 压缩类型
     * @param compressEnum 压缩模式
     */
    @Override
    public void compress(String pas, String path, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path,path,path,pas,fileTypeCMDEnum,compressEnum);
    }

    /**
     *
     * @param pas
     * @param path
     * @param newPath
     * @param fileTypeCMDEnum
     * @param compressEnum
     */
    @Override
    public void compress(String pas, String path, String newPath, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path,path,newPath,pas,fileTypeCMDEnum,compressEnum);
    }

    private static void recursionFile(String path,String oldPath,String newPath, String pas, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum){
        File file = new File(path);
        // 获取该文件夹下的所有文件
        File[] fileList = file.listFiles();
        // 便利所有的文件夹
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                //dealFile(fileList[i]);
                //consumer.accept(fileList[i]);
                compressFile(fileList[i],oldPath,newPath,pas,fileTypeCMDEnum,compressEnum);
            }
        }
    }

    private static void compressFile(File file,String oldPath,String newPath,String pas,FileTypeCMDEnum fileTypeCMDEnum,CompressEnum compressEnum){
        String absolutePath = file.getAbsolutePath();

        if(file.isDirectory()){
            if(compressEnum.ISCD){
                // 重复当前动作
                recursionFile(absolutePath,oldPath,newPath,pas,fileTypeCMDEnum,compressEnum);
            }else {
                String password = pas;
                String sourceFile = absolutePath + CompressConstant.all;
                //String targetFile = absolutePath + fileTypeCMDEnum.type;
                String targetFile = absolutePath.replace(oldPath,newPath);
                // TODO 执行压缩命令
                doCompress(pas,fileTypeCMDEnum.cmd_7z,targetFile,sourceFile);
            }
        }else {
            /**
             * 如果是每个文件都要逐级压缩，在就在最顶级生成一个文件夹
             *  /a/a/b
             *  例如7z
             *  /a/a/b7z
             */
            String sourceFile = absolutePath;
            if(compressEnum.ISCD){
                absolutePath = absolutePath.replace(oldPath,newPath + fileTypeCMDEnum.toString());
            }
            String password = pas;
            String targetFile = absolutePath + fileTypeCMDEnum.type;
            // TODO 执行压缩命令
            doCompress(password,fileTypeCMDEnum.cmd_7z,targetFile,sourceFile);
        }

    }

    private static void doCompress(String pas,String type_7z,String targetFile,String sourceFile){

        /*targetFile = targetFile.replaceAll(" ", "\" \"").replaceAll("&","");
        sourceFile = sourceFile.replaceAll(" ", "\" \"").replaceAll("&","");*/

        /*targetFile = targetFile.replaceAll("&","");
        sourceFile = sourceFile.replaceAll("&","");*/

        if(RStringUtils.isNotEmpty(pas)){
            pas = "-p" + pas;
        }else {
            pas = "";
        }
        String cmd = String.format(CompressConstant.compressionCommand,
                type_7z,
                targetFile,
                sourceFile,
                pas);

        //CmdUtils.execCmd(cmd);
        System.out.println("cmd: " + cmd);
        // String result = CmdUtils.execProcess(cmd);
        String result = CmdUtils.execRuntime(cmd);
        System.out.println("执行结果: " + result);
    }

}
