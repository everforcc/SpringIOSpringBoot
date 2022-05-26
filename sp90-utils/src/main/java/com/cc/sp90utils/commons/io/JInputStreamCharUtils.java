package com.cc.sp90utils.commons.io;

import com.cc.sp90utils.exception.Code;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Yukino
 * 2020/7/3
 * 写入操作
 */
public class JInputStreamCharUtils {

    private String fileName;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JInputStreamCharUtils() {
    }

    /**
     *
     * @param path
     * @param fileName
     */
    public JInputStreamCharUtils(String path, String fileName) {
        // 后缀可能有文件名，但是路径应该不会有点，所以分开它
        /*if(path.contains("\\.")){
            path=path.substring(0,path.lastIndexOf(File.separator));
            System.out.println(path);
        }*/
        this.fileName=fileName;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        this.path = path;
    }


    /**
     * 系统文件 文件末尾追加
     */
    public void IO_PrintWriter_Append(String path, String content) { this.writeAppend(path,new String[]{content}); }

    public void IO_PrintWriter_Append(String content) { this.writeAppend(null,new String[]{content}); }

    public void IO_PrintWriter_Append(String path, String[] content) {
        this.writeAppend(path,content);
    }

    public void IO_PrintWriter_Append(String[] content) { this.writeAppend(null,content); }

    private void writeAppend(String path, String content[]){
        if(StringUtils.isEmpty(path)){
            path=this.path; // 这里处理的还是有问题，也有可能没初始化path也过来，再说吧
        }
        File f = new File(path+this.fileName);

        // web乱码 printWriter=new PrintWriter(new OutputStreamWriter(new FileOutputStream(ndfFileName), "UTF-8"));
        try (FileWriter fw = new FileWriter(f, true);
             PrintWriter pw = new PrintWriter(fw)){
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            //File f=new File(IFilePath.pathRoot() + "/a.txt");
            // java.io.FileNotFoundException: C:\NEW\ideaProject\aly\OneForAll\temp\古文名句\《楞严经》.txt (另一个程序正在使用此文件，进程无法访问。)
            // 总是开启关闭浪费资源，直接组装好给放进来

            for(String str:content) {
                pw.println(str);
            }
        } catch (IOException e) {
            throw Code.A00001.toUserException(e);
        }

    }

}
