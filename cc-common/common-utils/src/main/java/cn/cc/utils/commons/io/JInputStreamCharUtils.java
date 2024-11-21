package cn.cc.utils.commons.io;

import cn.cc.utils.exception.Code;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JInputStreamCharUtils {

    private String fileName;
    private String path;

    private String absolutePath;


    private JInputStreamCharUtils() {
    }

    /**
     * @param path     文件路径
     * @param fileName 文件名
     */
    public JInputStreamCharUtils(String path, String fileName) {
        this.fileName = fileName;
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            log.info("文件夹【{}】创建结果 {}", path, mkdir);
        }
        this.path = path;
    }

    /**
     * @param absolutePath 完整路径
     */
    public JInputStreamCharUtils(String absolutePath) {
        this.absolutePath = absolutePath;
    }


//    public void printWriterAppend(String path, String content) {
//        this.writeAppend(path, new String[]{content});
//    }

    /**
     * 系统文件 文件末尾追加
     */
    public void printWriterAppend(String content) {
        this.writeAppend(new String[]{content});
    }

//    public void printWriterAppend(String path, String[] content) {
//        this.writeAppend(path, content);
//    }

    public void printWriterAppend(String[] content) {
        this.writeAppend(content);
    }

    private void writeAppend(String content[]) {
        File f;
        // 对应两种初始化方式
        if (StringUtils.isEmpty(absolutePath)) {
            f = new File(path + File.separator + this.fileName);
        }else {
            f = new File(absolutePath);
        }

        // web乱码 printWriter=new PrintWriter(new OutputStreamWriter(new FileOutputStream(ndfFileName), "UTF-8"));
        try (FileWriter fw = new FileWriter(f, true);
             PrintWriter pw = new PrintWriter(fw)) {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            //File f=new File(IFilePath.pathRoot() + "/a.txt");
            // java.io.FileNotFoundException: C:\NEW\ideaProject\aly\OneForAll\temp\古文名句\《楞严经》.txt (另一个程序正在使用此文件，进程无法访问。)
            // 总是开启关闭浪费资源，直接组装好给放进来

            for (String str : content) {
                pw.println(str);
            }
        } catch (IOException e) {
            throw Code.A00001.toUserException(e);
        }

    }

}
