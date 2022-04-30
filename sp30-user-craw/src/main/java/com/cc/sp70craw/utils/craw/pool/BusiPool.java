package com.cc.sp70craw.utils.craw.pool;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BusiPool {

    /**
     * 存放全局的 BusiPool 示例
     */
    private static Map<String,BusiPool> busiPoolMap = new ConcurrentHashMap<>();
    //private static Map<String,String> lockMap = new ConcurrentHashMap<>();
    /**
     * 创建对象锁
     */
    private static String singleBusiPool = "singleBusiPool";

    /**
     * 业务池子
     * 主要用来存链接
     *
     */

    /**
     * 网站根目录
     * 业务锁
     */
    @Getter
    private String root;

    //private static String lock = "abc";

    private List<BusiTag> VIRGIN_List = new ArrayList<>();
    private List<BusiTag> EXECUTED_List = new ArrayList<>();
    private List<BusiTag> CANCELLED_List = new ArrayList<>();
    private List<BusiTag> SUCCESS_List = new ArrayList<>();
    private List<BusiTag> FAIL_List = new ArrayList<>();

    /**
     * 待执行任务数
     * @return
     */
    public int VIRGIN_List_Size(){
        return VIRGIN_List.size();
    }

    public boolean addTag(String TagUrl){
        return addTag(new BusiTag(TagUrl));
    }

    @SneakyThrows
    public boolean addTag(BusiTag busiTag){
        log.info("当前锁: " + singleBusiPool);
        synchronized (singleBusiPool) {
            log.info("进入锁: " + singleBusiPool);
            log.info(">>> start: " + root);
//            if("abcd".equals(root)){
//                Thread.sleep(10000);
//            }

            log.info("出锁: " + singleBusiPool);
            if (VIRGIN_List.contains(busiTag)) {
                log.info("VIRGIN_List  end: " + root);
                return false;
            }
            if (EXECUTED_List.contains(busiTag)) {
                log.info("EXECUTED_List <<< end: " + root);
                return false;
            }
            if (CANCELLED_List.contains(busiTag)) {
                log.info("CANCELLED_List <<< end: " + root);
                return false;
            }
            if (SUCCESS_List.contains(busiTag)) {
                log.info("SUCCESS_List <<< end: " + root);
                return false;
            }
            if (FAIL_List.contains(busiTag)) {
                log.info("FAIL_List <<< end: " + root);
                return false;
            }
            VIRGIN_List.add(busiTag);
            log.info("VIRGIN_List <<< end: " + root);
            return true;
        }
    }

    /**
     * 如果锁一致，这个初始化不需要
     * @return
     */
    public boolean init(){
        synchronized (singleBusiPool){
            if(VIRGIN_List.size() == 0){
                // throw new RuntimeException("所有任务已执行完毕");
                return false;
            }else {
                return true;
            }
        }
    }

    public BusiTag getVIRGIN(){
        log.info("当前锁: " + singleBusiPool);
        synchronized (singleBusiPool){
            log.info("进入锁: " + singleBusiPool);
            int size = VIRGIN_List.size();
            log.info("size: " + size);
            if(size == 0){
                // throw new RuntimeException("所有任务已执行完毕");
                log.info("出锁: " + singleBusiPool);
                return null;
            }else {
                BusiTag busiTag = VIRGIN_List.get(0);
                VIRGIN_List.remove(busiTag);
                EXECUTED_List.add(busiTag);
                log.info("出锁: " + singleBusiPool);
             return busiTag;
            }

        }
    }

    public void canceled(BusiTag busiTag){
        synchronized (singleBusiPool){
            VIRGIN_List.remove(busiTag);
            CANCELLED_List.add(busiTag);
        }
    }

    public void success(BusiTag busiTag){
        synchronized (singleBusiPool){
            VIRGIN_List.remove(busiTag);
            SUCCESS_List.add(busiTag);
        }
    }

    public void fail(BusiTag busiTag){
        synchronized (singleBusiPool){
            VIRGIN_List.remove(busiTag);
            FAIL_List.add(busiTag);
        }
    }

    public static BusiPool singleBusiPool(String root){
        synchronized (singleBusiPool) {
            if (busiPoolMap.containsKey(root)) {
                return busiPoolMap.get(root);
            } else {
                BusiPool busiPool = new BusiPool(root);
                busiPoolMap.put(root,busiPool);
                //lockMap.put(root,root);
                return busiPool;
            }
        }
    }

    private BusiPool(String root) {
        this.root = root;
    }

    /**
     * 网站的页面
     */
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusiTag{
        /**
         * 未处理
         */
        @Setter
        @Getter
        private int flag = VIRGIN;

        /**
         * 初始化，未处理
         */
        static final int VIRGIN = 0;

        /**
         * 该任务正在执行中
         */
        static final int EXECUTED = 2;

        /**
         * 取消任务
         */
        static final int CANCELLED   = 3;

        /**
         * 任务执行成功
         */
        static final int SUCCESS = 4;

        /**
         * 任务执行失败
         */
        static final int FAIL = 5;

        /**
         * 如果没成功要说明原因
         */
        @Getter
        @Setter
        private String reason;

        /**
         * 网址
         */
        @Setter
        @Getter
        private String url;

        public BusiTag(String url) {
            this.url = url;
        }

        /**
         * 只需要对比url,不需要flag
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            BusiTag busiTag = (BusiTag)obj;
            return this.url.equals(busiTag.getUrl());
        }
    }

}
