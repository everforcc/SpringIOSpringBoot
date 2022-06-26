/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-26 15:26
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import cn.cc.sp31usercraw.constant.FileConstant;
import cn.cc.sp31usercraw.dodo.DownDo;
import com.cc.sp90utils.commons.io.RFileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 按顺序实行下载速度太慢
 */
@Slf4j
public class DownUtils extends Thread {

    // 网站根目录
    private String rootUrl;
    // 标记是否新增链接结束,默认false
    private boolean addEnd = false;
    private List<DownDo> downDoList = new ArrayList<>();
    // 实例化的集合
    private static Map<String, DownUtils> downUtilsMap = new HashMap<>();

    public String getRootUrl() {
        return rootUrl;
    }

    // 如果当前小说所有链接都加进来了就设置true
    public void markAddEnd() {
        this.addEnd = true;
    }

    private DownUtils() {
    }

    private DownUtils(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    /**
     *
     * @param rootUrl 网站根目录
     * @return 返回bean
     */
    public static DownUtils instantce(String rootUrl) {
        synchronized (rootUrl){
            if(downUtilsMap.containsKey(rootUrl)){
                log.info("已存在 [{}] ", rootUrl);
                return downUtilsMap.get(rootUrl);
            }else {
                DownUtils downUtils = new DownUtils(rootUrl);
                downUtilsMap.put(rootUrl, downUtils);

                // 初始化之后开启线程
                Thread thread = new Thread(downUtils);
                thread.start();

                log.info("初始化 [{}] ", rootUrl);
                return downUtils;
            }
        }
    }

    public void add(DownDo downDo) {
        // 新增一个待下载的数据
        log.info("添加待下载的任务 [{}]", downDo.toString());
        downDoList.add(downDo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        DownUtils downUtils = (DownUtils) o;
        return Objects.equals(rootUrl, downUtils.rootUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootUrl);
    }

    @Override
    public void run() {

        // 1. addEnd默认false还没添加完毕
        // 2. 添加过程中可以进来
        // 3. 添加完毕后设置为true
        // 4.
        while (!addEnd || 0 != downDoList.size()) {
            //log.info("下载IO任务 [{}] [{}] [{}]", !addEnd, 0 != downDoList.size(), rootUrl);
            // 1. 校验size不等于0的时候进来处理
            if(downDoList.size()!=0) {
                try {
                    DownDo downDo = downDoList.get(0);
                    log.info("当前下载参数 [{}]", downDo.getPath());
                    RFileUtils.writeStringToFile(downDo.getPath(), downDo.getContent(), downDo.isAppend());
                    RFileUtils.writeStringToFile(FileConstant.fileRoot + "log/log.log", downDo.getPath() + "\r\n", true);
                    downDoList.remove(downDo);
                }catch (Exception e){
                    RFileUtils.writeStringToFile(FileConstant.fileRoot + "log/log.log", e.toString() + "\r\n", true);
                }finally {

                }
            }
        }
        log.info("下载IO任务 [{}] 结束 ", rootUrl);
    }


    public static void main(String[] args) {
//        List<String> stringList = new ArrayList<>();
//        stringList.add("a");
//        stringList.add("b");
//        stringList.add("c");
//        stringList.add("d");
//        System.out.println(stringList.size());
//        System.out.println(stringList.get(0));
//        System.out.println(stringList.remove(0));
//        stringList.add("e");
//        System.out.println(stringList.get(0));
//        System.out.println(stringList.remove(0));
//        System.out.println(stringList.contains("c"));

        DownUtils downUtils_a = DownUtils.instantce("a");
        //DownUtils downUtils_b = DownUtils.instantce("b");
        DownUtils downUtils_a_1 = DownUtils.instantce("a");
        DownDo downDo = new DownDo();
        downDo.setPath("abc");
        downUtils_a.add(downDo);
        downUtils_a.add(downDo);
        downUtils_a.add(downDo);
        downUtils_a.add(downDo);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("markAddEnd()");
        downUtils_a.markAddEnd();


//        List<DownUtils> downUtilsList = new ArrayList<>();
//        downUtilsList.add(downUtils_a);
//        System.out.println(downUtilsList.contains(downUtils_a));
//        System.out.println(downUtilsList.contains(downUtils_b));
//        System.out.println(downUtilsList.contains(downUtils_a_1));
//
//        downUtilsList.add(downUtils_b);
//        System.out.println(downUtilsList.contains(downUtils_a));
//        System.out.println(downUtilsList.contains(downUtils_b));
//        System.out.println(downUtilsList.contains(downUtils_a_1));

    }

}
