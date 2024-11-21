package cn.cc.utils.exception;


public enum AppCode implements ICode {
    /**
     * A00000 系列为系统公共异常 Code
     * A00100 A00200 系列为用户异常,和测试代码使用异常
     * A00201 A00300 数据库相关异常
     * A00201 A00300 数据库相关异常
     * A00301 A00400 数据校验异常
     */
    A00100("自定义异常，可以使用comment替换"),
    
    ;
    /**
     * 枚举属性说明
     */
    public final String comment;

    AppCode(String comment) {
        this.comment = comment;
    }


    @Override
    public String getComment() {
        return this.comment;
    }



}
