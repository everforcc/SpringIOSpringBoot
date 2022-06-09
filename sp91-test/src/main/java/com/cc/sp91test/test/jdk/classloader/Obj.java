package com.cc.sp91test.test.jdk.classloader;

/**
 * 对象常用的字段方法
 *
 * @author everforcc 2021-09-16
 */
public class Obj {

    // 属性
    public String publicStr;
    private String privateStr = "privateStr---";
    public int publicint;
    private int privateint;

    /*get set*/

    public String getPublicStr() {
        return publicStr;
    }

    public void setPublicStr(String publicStr) {
        this.publicStr = publicStr;
    }

    public String getPrivateStr() {
        return privateStr;
    }

    public void setPrivateStr(String privateStr) {
        this.privateStr = privateStr;
    }

    public int getPublicint() {
        return publicint;
    }

    public void setPublicint(int publicint) {
        this.publicint = publicint;
    }

    public int getPrivateint() {
        return privateint;
    }

    public void setPrivateint(int privateint) {
        this.privateint = privateint;
    }

    /*get set*/

    // 方法

    public void methodVoidNull() {

    }

    public void methodVoidParams(String str) {
        System.out.println("公有方法 参数[String] : " + str);
    }

    private void methodPrivateVoidNull() {

    }

    private void methodPrivateVoidParams(String str) {
        System.out.println("私有方法 参数[String] : " + str);
    }

    public String methodStringNull() {
        return "";
    }

    public String methodStringParams(String str) {
        return "";
    }

    // 构造


    public Obj(String publicStr) {
        this.publicStr = publicStr;
    }

    public Obj() {

    }

    @Override
    public String toString() {
        return "Obj{" +
                "publicStr='" + publicStr + '\'' +
                ", privateStr='" + privateStr + '\'' +
                ", publicint=" + publicint +
                ", privateint=" + privateint +
                '}';
    }

    /**
     * 内部类
     */
    public class InnerClass {

        private String privateInnerStr = "privateInnerStr123";
        public String publicInnerStr = "privateInnerStr123";

        public InnerClass() {
        }

        public InnerClass(String privateInnerStr, String publicInnerStr) {
            this.privateInnerStr = privateInnerStr;
            this.publicInnerStr = publicInnerStr;
        }

    }

}
