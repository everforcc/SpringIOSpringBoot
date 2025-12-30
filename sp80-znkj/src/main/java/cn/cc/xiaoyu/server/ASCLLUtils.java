package cn.cc.xiaoyu.server;

/**
 * 字符数值转 ASCLL
 */
public class ASCLLUtils {


    public static void main(String[] args) {
        // 1. 字符转ASCII数值（自动类型转换）
        char ch = '8'; // 定义一个字符
        int asciiValue = ch; // char自动转为int，得到对应的ASCII值
        System.out.println("字符 '" + ch + "' 对应的ASCII值: " + asciiValue); // 输出：65

        // 2. ASCII数值转字符（强制类型转换）
        int num = 0x38; // 定义一个ASCII数值（对应小写a）
        char charFromAscii = (char) num; // int强制转为char
        System.out.println("ASCII值 " + num + " 对应的字符: " + charFromAscii); // 输出：a

        // 扩展：遍历打印常用ASCII字符（0-127）
        System.out.println("\n常用ASCII字符对照表（部分）：");
        for (int i = 32; i <= 126; i++) { // 32是空格，126是~，覆盖可见字符
            System.out.print(i + " -> " + (char) i + "  ");
            if (i % 10 == 0) { // 每10个换行，方便查看
                System.out.println();
            }
        }
    }


}
