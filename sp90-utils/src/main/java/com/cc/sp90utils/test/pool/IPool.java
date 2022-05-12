package com.cc.sp90utils.test.pool;

import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * 没有提供池，但是需要效率的自定义
 */
public class IPool {

    /**
     * 池对象最大最小值
     */
    private int max = 2;
    private int min = 1;
    private int current = 0;

    private int waitTime = 1 * 1000;
    private int waitCurrentCount = 0;
    private int waitMaxCount = 3;
    private String waitError = "任务忙碌，请重试";

    private PoolVO t;

    /**
     * 保存池
     *
     * 池子可以加入时间信息
     * 用stream排序，时间太长的就抛出异常，然后调用者就抛出异常
     */
    private List<PoolVO> freeListT = new Vector<>();
    private List<PoolVO> activeListT = new Vector<>();

    /**
     * 单例对象
     */
    private static IPool instance;

    /**
     * 修改 - 锁
     */
    public static Object lock = new Object();
    private static boolean ninLock = false;

    /**
     * 获取本类 实例
     * @return
     */
    public static IPool getInstance(){
        if(ninLock){
            return instance;
        }
        synchronized (lock) {
            if (instance == null) {
                System.out.println("初始化[instantce]");
                instance = new IPool();
            }
            System.out.println("已有 [instantce]");
            return instance;
        }

    }

    /**
     * 获取 池 中 对象
     * @return
     */
    public PoolVO getT(){
        PoolVO poolVO = getFreeT();
        if(Objects.nonNull(poolVO)){
            return poolVO;
        }else {
            return getNewT();
        }
    }




    /**
     * 客户端自己实现
     * @return
     */
    //public abstract PoolVO createT();

    /**
     * 创建 新池中对象
     * @return
     */
    private PoolVO crreateT(){
        return new PoolVO(current++);
    }

    /**
     * 初始化池子
     */
    private IPool(){
        if(ninLock){
            return;
        }
        synchronized (lock) {
            while (this.min > this.current) {
                this.freeListT.add(crreateT());
            }
        }
    }



    /**
     * 获取 池子中 空闲的 对象
     * @return
     */
    private PoolVO getFreeT(){
        if(ninLock){
            return null;
        }
        synchronized (lock) {
            if (freeListT.size() > 0) {
                /**
                 * 移除空闲队列，加入活动队列
                 */
                PoolVO t = freeListT.remove(0);
                activeListT.add(t);
                return t;
            }
            return null;
        }
    }

    /**
     * 获取池中对象
     * @return
     */
    private PoolVO getNewT(){
        if(ninLock){
            return null;
        }
        synchronized (lock) {
            // 还没到上限
            if (current < max) {
                PoolVO poolVO = crreateT();
                this.activeListT.add(poolVO);
                return poolVO;
            } else {
                // 到 上限 了，等一会重新获取
                try {
                    if(waitCurrentCount++ < waitMaxCount) {
                        System.out.println("到上限了，等待" + waitTime + "ms ,重新获取: 剩余次数" + (waitMaxCount-waitCurrentCount));
                        Thread.sleep(waitTime);
                        getNewT();
                    }
                } catch (InterruptedException e) {
                    waitCurrentCount = 0;
                    throw new RuntimeException(e);
                }

                waitCurrentCount = 0;
                System.err.println(waitError);
                throw new RuntimeException(waitError);
            }
        }
    }

    /**
     * 必须手动关闭，参考
     *  EntityUtils.consume(entity);
     *  需要手动关闭
     * {@link EntityUtils}
     * @param poolVO
     */
    public void closeT(PoolVO poolVO){
        if(ninLock){
            return;
        }
        synchronized (lock) {
            activeListT.remove(poolVO);
            freeListT.add(poolVO);
        }
    }
    public void closeAll(){
        //synchronized (lock) {
            ninLock = true;
            System.out.println("正在清空活动对象");
//            while (activeListT.size() > 0) {
//                PoolVO poolVO_0 = activeListT.get(0);
//                if(1 == poolVO_0.getIndex()) {
//                    // 使调用者抛出异常，中断使用
//                    //if (1 == poolVO_0.getIndex()) {
//                        poolVO_0.setException();
//                    //}
//
//                    PoolVO poolVO = activeListT.remove(0);
//                    freeListT.add(poolVO);
//                    current--;
//                }
//            }

            for(int i=0;i<activeListT.size();i++){
                PoolVO poolVO = activeListT.get(i);
                if(1 == poolVO.getIndex()) {
                    // 使调用者抛出异常，中断使用
                    //if (1 == poolVO_0.getIndex()) {
                    System.out.println("移除: " + poolVO.getIndex());
                    poolVO.setException();
                    //}
                    activeListT.remove(poolVO);
                    freeListT.add(poolVO);
                    current--;
                }
            }
            System.out.println("空闲对象: " + freeListT.size() + "活动对象: " + activeListT.size());
            ninLock = false;
        //}
    }

}
