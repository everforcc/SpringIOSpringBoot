package cmd.compress.impl;

import cmd.compress.CompressConstant;
import cmd.compress.CompressEnum;
import cmd.compress.FileTypeCMDEnum;
import cmd.compress.ISevenUtils;
import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.runtime.CmdUtils;
import java.io.File;

public class SevenUtilsCMD implements ISevenUtils {

    /**
     * TODO 还没有对文件夹和文件名进行校验
     * @param pas
     * @param path
     * @param fileTypeCMDEnum
     * @param compressEnum
     */
    @Override
    public void compress(String pas, String path, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path,path,pas,fileTypeCMDEnum,compressEnum);
    }

    @Override
    public void compress(String pas, String path, String initPath, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum) {
        recursionFile(path,initPath,pas,fileTypeCMDEnum,compressEnum);
    }

    private static void recursionFile(String path,String initPath, String pas, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum){
        File file = new File(path);
        // 获取该文件夹下的所有文件
        File[] fileList = file.listFiles();
        // 便利所有的文件夹
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                //dealFile(fileList[i]);
                //consumer.accept(fileList[i]);
                compressFile(fileList[i],initPath,pas,fileTypeCMDEnum,compressEnum);
            }
        }
    }

    private static void compressFile(File file,String initPath,String pas,FileTypeCMDEnum fileTypeCMDEnum,CompressEnum compressEnum){
        String absolutePath = file.getAbsolutePath();

        if(file.isDirectory()){
            if(compressEnum.ISCD){
                // 重复当前动作
                recursionFile(absolutePath,initPath,pas,fileTypeCMDEnum,compressEnum);
            }else {
                String password = pas;
                String sourceFile = absolutePath + CompressConstant.all;
                String targetFile = absolutePath + fileTypeCMDEnum.type;
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
            if(compressEnum.ISCD){
                absolutePath = absolutePath.replace(initPath,initPath + fileTypeCMDEnum.toString());
            }
            String password = pas;
            String sourceFile = absolutePath;
            String targetFile = absolutePath + fileTypeCMDEnum.type;
            // TODO 执行压缩命令
            doCompress(pas,fileTypeCMDEnum.cmd_7z,targetFile,sourceFile);
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
        String result = CmdUtils.execProcess(cmd);
        //System.out.println("执行结果: " + result);
    }

}
