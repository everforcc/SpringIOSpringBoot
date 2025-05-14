package cn.cc;
import cn.cc.app.MainApp;

/**
 * 应用程序启动入口类（补充说明）
 * 设计模式：门面模式(Facade Pattern)的轻量级实现
 * 安全机制：异常全局捕获，防止启动崩溃
 */
public class Launcher {
    /**
     * 主启动方法（补充说明）
     * @param args 命令行参数 
     * 设计要点：不直接持有MainApp实例，通过静态方法调用实现松耦合
     */
    public static void main(String[] args) {
        // 异常处理策略：捕获所有未处理异常并打印堆栈
        try {
            MainApp.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}