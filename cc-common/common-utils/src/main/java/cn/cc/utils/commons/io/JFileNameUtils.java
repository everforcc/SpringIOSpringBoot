/**
 * Project:TODO ADD PROJECT NAME OneForAll
 *
 * @Description
 * @Author everforcc
 * @Date 2022-05-26 09:22
 * Copyright
 */

package cn.cc.utils.commons.io;

public class JFileNameUtils {

    public static String checkFileName(String fileName){
        //在Windows系统中，文件名命名规则如下：
        //1）文件名最长可以使用255个字符；
        //2）可以使用扩展名，扩展名用来表示文件类型，也可以使用多间隔符的扩展名（如win.ini.txt是一个合法的文件名，但其文件类型由最后一个扩展名决定）；
        //3）文件名中允许使用空格，但不允许使用下列字符（英文输入法状态）：< > / \ | : " * ?；
        //4）windows系统对文件名中字母的大小写在显示时有不同，但在使用时不区分大小写。
        String pattern="\\<*\\>*\\/*\\\\*\\|*\\:*\"*\\**\\?*\\；*\\ *";

        fileName=fileName.replaceAll(pattern,"");
        if(fileName.length()>255){
            return fileName.substring(0,250);
        }
        return fileName;
    }

}
