package com.cc.sp70craw.utils.craw;

import com.cc.sp70craw.utils.craw.business.BusinessPattern;
import com.cc.sp70craw.utils.craw.data.IDataParse;
import com.cc.sp70craw.utils.craw.data.impl.JsoupIDataParse;
import com.cc.sp70craw.utils.craw.http.IHttp;
import com.cc.sp70craw.utils.craw.http.impl.HttpClientIHttp;
import com.cc.sp70craw.utils.craw.pool.BusiPool;
import com.cc.sp70craw.utils.craw.pool.ThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
public class FlowMain {

    /**
     * TODO 需要关闭，如何关闭
     * 线程池处理
     */
    private static ExecutorService executorService = ThreadPool.getPool();

    /**
     * 网络请求
     * 不需要关闭
     */
    private static IHttp iHttp = new HttpClientIHttp();

    /**
     * 数据处理
     */
    private static IDataParse IDataParse = new JsoupIDataParse();

    /**
     * 下面展示流程
     * @param args
     */
    public static void main(String[] args) {

        /**
         * 1. 手动设置初始化链接
         * 将链接加入队列
         */
        String root = "http://www.3diyibanzhu.xyz/";
        String startUrl = "http://www.3diyibanzhu.xyz/20/20412/";
        BusiPool busiPool = BusiPool.singleBusiPool(root);

        /**
         * 初始化数据
         */
        addUrl(busiPool,startUrl);

        BusiPool.BusiTag busiTag = getVIRGIN(busiPool);

        {
            // 打印当前需要处理的信息
//            log.info("busiTag.getUrl(): " + busiTag.getUrl());
//            log.info("busiTag.getFlag(): " + busiTag.getFlag());

            /**
             * TODO 先把接口列出来，数据再说
             * 这一快用接口方法独立出来，
             * public abstract dealData();
             */

            //String html = getHtmlByUrl(busiTag.getUrl());
            String html = getHtmlByUrl("http://www.3diyibanzhu.xyz/20/20412/701424_5.html");

            log.info(html);
            String content_1 = IDataParse.parseHTMLStr(html,BusinessPattern.page_content_1);
            //String content_2 = dataParse.parseHTMLStr(html,BusinessPattern.page_content_2);
            log.info("---");
            log.info(content_1);
            //IDataParse.save("701424_5.txt",content_1);
            log.info("---");
            //log.info(content_2);

//            String html = getHtmlByUrl(busiTag.getUrl());
//            log.info("返回html");
//
//            log.info(html);
            /**
             * 不同的url，需要对应不同的数据处理器
             * ，要为每一种url定义一个，根据规则来判断使用哪个
             */

            // 结束标志
            busiTag = getVIRGIN(busiPool);
        }//while (Objects.nonNull(busiTag));

        log.info("数据获取完毕");
    }

    /**
     * 发现链接并加入队列
     * TODO 就怕加入后，还没来得及取出来
     */
    public static void addUrl(BusiPool busiPool,String tagUrl){
        executorService.execute(new AddUrl(busiPool,tagUrl));
        //while (!init(busiPool)) {}
    }

    /**
     * 初始化队列
     * @param busiPool
     * @return
     */
    public static boolean init(BusiPool busiPool){
        return busiPool.init();
    }

    /**
     * 获取队列中的 待处理URL
     * @param busiPool
     * @return
     */
    public static BusiPool.BusiTag getVIRGIN(BusiPool busiPool){
        return busiPool.getVIRGIN();
    }

    /**
     * 根据链接获取html
     * @param url
     * @return
     */
    public static String getHtmlByUrl(String url){
        return iHttp.requestUrl(url);
    }

    public static void dealData(BusiPool busiPool,String url,String html){
        /**
         * 根据url来选择不同的处理器
         * 来处理html，并加入队列
         */

        /**
         * 处理数据并保存
         * 然后把待处理的链接加入队列
         * http://www.3diyibanzhu.xyz/20/20412_3/
         * http://www.3diyibanzhu.xyz/20/20412/701424.html
         */
        if(url.startsWith("http://www.3diyibanzhu.xyz/20/20412_")){
            //分页

            /**
             * 下一页
             */
            String nextPage = IDataParse.parseHTMLStr(html,BusinessPattern.menu_nextpage);
            /**
             * 章节内容链接
             */
            List<String> stringList = IDataParse.parseHTMLList(html, BusinessPattern.menu_list);

            addUrl(busiPool,nextPage);

            for(String str:stringList){
                addUrl(busiPool,str);
            }

            /**
             * 1. 取出内容链接
             * 2. 取出分页链接
             */
        }else if (url.startsWith("http://www.3diyibanzhu.xyz/20/20412/")){
            // 内容
            /**
             * 1. 取出网页内容/markdown, 实现类+1
             * 放入线程处理
             *
             * 2. 取出下一页链接
             * 存入链接
             */

            /**
             * 内容
             */
            String content = IDataParse.parseHTMLStr(html,BusinessPattern.page_content_1);
            /**
             * 下一页
             */
            List<String> pageList = IDataParse.parseHTMLList(html,BusinessPattern.page_next);


        }

    }

    /**
     * 1. 将链接存入 队列中
     */
    public static void addUrlc(BusiPool busiPool,String url){

        /**
         * 将链接
         */
        ExecutorService executorService = ThreadPool.getPool();

        Runnable runnable_a = new Runnable() {
            @Override
            public void run() {

                BusiPool busiPool_1 = BusiPool.singleBusiPool("abc");
                busiPool_1.addTag("abcdabcd");
                log.info("abc: " + busiPool_1.VIRGIN_List_Size());
            }
        };

        Runnable runnable_b = new Runnable() {
            @Override
            public void run() {
                BusiPool busiPool_1 = BusiPool.singleBusiPool("abcd");
                busiPool_1.addTag("abcdabcd");
                log.info("abcd: " + busiPool_1.VIRGIN_List_Size());
            }
        };

        Runnable runnable_c = new Runnable() {
            @Override
            public void run() {
                BusiPool busiPool_1 = BusiPool.singleBusiPool("abc");
                busiPool_1.addTag("abcdabcdefg");
                log.info("abc: " + busiPool_1.VIRGIN_List_Size());
            }
        };

        executorService.execute(runnable_a);
        executorService.execute(runnable_a);
        executorService.execute(runnable_b);
        executorService.execute(runnable_a);
        executorService.execute(runnable_a);

        executorService.execute(runnable_c);
        executorService.execute(runnable_c);
    }



    public static class AddUrl implements Runnable{

        private BusiPool busiPool;

        private String tagUrl;

        public AddUrl(BusiPool busiPool, String tagUrl) {
            this.busiPool = busiPool;
            this.tagUrl = tagUrl;
        }

        @Override
        public void run() {
            busiPool.addTag(tagUrl);
        }
    }

}
