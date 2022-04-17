package com.cc.sp70craw.utils.craw.business;

import com.cc.sp70craw.utils.craw.http.IHttp;
import com.cc.sp70craw.utils.craw.http.impl.HttpClientIHttp;
import com.cc.sp70craw.utils.craw.http.impl.SeleniumIHttp;
import com.cc.sp70craw.utils.craw.io.IData;
import com.cc.sp70craw.utils.craw.io.impl.FileIData;
import com.cc.sp70craw.utils.craw.pool.BusiPool;
import com.cc.sp70craw.utils.craw.pool.ThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 每个网站仅需要实现一个该类
 */
@Slf4j
public abstract class IBusiness<T extends BusiPool> extends Thread{

    /**
     * 定义每种业务的流程
     *
     * 1. 发请求,返回数据
     * 2. 处理数据
     * 3. 保存数据
     */

    private T t;

    public IBusiness(T t) {
        this.t = t;
    }

    /**
     * 数据处理流程
     */
    private void flow(){
        /**
         * 1. 初始化
         * initRoot();
         * 2. 从队列中取出待处理的url
         * getVIRGIN()
         * 3. 从url取出数据
         * getHtmlByUrl
         * 4. deal
         */

    }

    /**
     * 1. 初始化网站起始数据
     *
     * 此时t已经有了root，数据，tag数据也定义好了
     */
    public boolean addQuene(String tag) {
        // 将 链接 加入队列
        //threadPool().execute(new AddQuene<T>(t,tag));
        // 这个位置多线程的话 无法保证哪个先运行，
        return t.addTag(tag);
        // t.init();
    }

    public void saveSingle(String path,String content) {
        // 将 链接 加入队列
        threadPool().execute(new SaveSingle(path,content,iSave()));
        // 这个位置多线程的话 无法保证哪个先运行，
    }

    /**
     * 从队列中拿出一个待处理的url
     * @return
     */
    public String getVIRGIN(){
        BusiPool.BusiTag busiTag = t.getVIRGIN();
        if(Objects.nonNull(busiTag)){
            return busiTag.getUrl();
        }
        return null;
    }



    public ExecutorService threadPool(){
        return ThreadPool.getPool();
    }

    /**
     * 处理web请求
     * @return
     */
    public IHttp iHttp(){
        return iHttp(null);
    }
    public IHttp iHttp(IHttp iHttp){
        if(iHttp == null){
            iHttp = new HttpClientIHttp();
        }
        return iHttp;
    }
    /**
     * 从url获取html
     * @param url
     * @return
     */
    public String getHtmlByUrl(String url){
        return iHttp().requestUrl(url);
    }

    public String getSeleniumHtmlByUrl(String url){
        return iHttp(new SeleniumIHttp()).requestUrl(url);
    }

    public abstract void dealData(String url,String data);

    /**
     * 保存数据
     */
    public IData iSave(){
        return iSave(null);
    }
    public IData iSave(IData iData){
        if(iData == null){
            iData = new FileIData();
        }
        return iData;
    }
    public void saveFile(String path,String content){
        iSave().save(path,content);
    }

    public void test(){
        test_1();
    }
    public void test_1(){
        log.info("abstract_测试");
    }

    /**
     * 业务类具体来实现
     * 小说为例,以小说为单位1
     */
    @Override
    public abstract void run();

    /**
     * 将链接加入队列
     */
    public static class AddQuene<T extends BusiPool> implements Runnable{

        private T t;

        private String tagUrl;

        public AddQuene(T t, String tagUrl) {
            this.t = t;
            this.tagUrl = tagUrl;
        }

        @Override
        public void run() {
            t.addTag(tagUrl);
        }

    }
    public static class SaveSingle implements Runnable{

        private String path;

        private String content;

        private IData iData;

        public SaveSingle(String path, String content, IData iData) {
            this.path = path;
            this.content = content;
            this.iData = iData;
        }

        @Override
        public void run() {
            log.info("正在保存: " + path);
            iData.save(path,content);
            log.info("保存完成: " + path);
        }

    }


}
