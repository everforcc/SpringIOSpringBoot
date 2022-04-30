package algo.busi;

import java.math.BigDecimal;

/**
 * @author c.c.
 * @date 2020/12/17
 */
public class MinStep {
    public static void main(String[] args) {
        // 用这个比较准确
        //summation(10);
        minStep();
    }

    // 粗调，细调， 动态规划
    //1. 100 层楼，两个球， 扩展为无穷个球，球无限的情况下为 2分法
    public static void minStep(){
        // 方法1
        // bilibili解法，没看明白
        // 假如层数为x层，最小次数为(√(8x+1)-1)/2向上取整。带入x=100可得最小次数14次。
        // 原理：当n(n+1)/2<x<(n+1)(n+2)/2时，n<(√(8x+1)-1)/2<n+1

        // 方法2
        // 假设次数为n，第一次为n,第二次为2n-1,3n-3, 得到公式 n^2 + n >=200
        int total = 17;
        int n = 0;
        while ((n * n + n)<(total * 2)){ //分析过程在笔记
            n++;
        }
        System.out.println("步长:" + n);

        // 假如分三次, 不会，先不弄了，的在本子上分析，电脑不好用

    }

    //2. 问题二，概率求总数 进度1/2
    /**
     公式
     $$
     f_N(0)=N\sum_1^n\frac{1}{i}
     $$
     */
    // 最终目的，计算出对方的数据总量
    // 此方法,在已知数据总量的情况下，多少次能取出全部数据
    public static void summation(int scale){
        int N = 1000; // 假设有1000个数据
        BigDecimal denominator = new BigDecimal(1);
        BigDecimal result = new BigDecimal(1);
        for(int i = 1;i <= N;i++){
            // 每次 1/i 递增的值累加
            result = result.add(denominator.divide(new BigDecimal(i), scale, BigDecimal.ROUND_HALF_UP));
        }
        // 最后 * 外面的 N
        result = result.multiply(new BigDecimal(N));
        System.out.println(result);
    }

    // 计算不准确
    public static void summation(){
       Double double1 = new Double(1000);
        Double sum = new Double(0);

        for(int i=1000;i>0;i--){
            //System.out.println(i);
            sum = double1/i + sum;
            //System.out.println(sum);
        }
        System.out.println(sum);
    }

}
