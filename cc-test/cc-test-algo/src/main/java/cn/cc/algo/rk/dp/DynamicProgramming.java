package cn.cc.algo.rk.dp;

/**
 * 动态规划
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        for (int n = 1; n <= 47; n++) {
            // 开始时间
            long startTime = System.currentTimeMillis();
//            long result = climb_stairs_recursive(n);
//            long result = climb_stairs_dp(n);
            long result = climb_stairs_optimized(n);
            // 结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("上到第" + n + "台阶，一共有" + result + "种可能性 耗时：" + (endTime - startTime) + "ms");

            long startTime2 = System.currentTimeMillis();
            long result2 = climb_stairs_memo(n);
            long endTime2 = System.currentTimeMillis();
            System.out.println("上到第" + n + "台阶，一共有" + result2 + "种可能性 耗时：" + (endTime2 - startTime2) + "ms");
        }
    }

    /**
     * 1. 暴力递归（低效）
     */
    public static long climb_stairs_recursive(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climb_stairs_recursive(n - 1) + climb_stairs_recursive(n - 2);
    }


    /**
     * 2. 带备忘录的递归（自顶向下）
     */
    public static long climb_stairs_memo(int n) {
        // 初始化备忘录数组，-1 表示未计算
        long[] memo = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1; // 填充初始值
        }
        // 调用辅助递归方法
        return helper(n, memo);
    }

    /**
     * 2.1. 递归辅助方法
     * 带备忘录的递归
     */
    private static long helper(int n, long[] memo){
        // 基础情况：1级台阶1种方法，2级台阶2种方法
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        // 如果备忘录中已有结果，直接返回，避免重复计算
        if(memo[n] != -1){
            return memo[n];
        }
        // 计算当前值并存入备忘录
        memo[n] = helper(n - 1, memo) + helper(n - 2, memo);
        return memo[n];
    }

    /**
     * 3. 递推数组（自底向上，标准DP）
     */
    public static long climb_stairs_dp(int n){
        // 动态规划（自底向上）
        if(n < 2){
            return n;
        }
        // 1. 定义状态数组：dp[i] 表示爬到第 i 级台阶的方法数
        long[] dp = new long[n + 1];
        // 2. 确定初始条件
        dp[1] = 1;
        dp[2] = 2;
        // 3. 状态转移：根据状态转移方程递推
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        // 4. 返回结果
        return dp[n];
    }

    /**
     * 4. 空间优化（滚动数组）
     */
    public static long climb_stairs_optimized(int n){
        // 动态规划（空间优化版）
        if(n < 2){
            return n;
        }
        // 1. 定义滚动数组：prev, curr
        long prev = 1;
        long curr = 2;
        for (int i = 3; i <= n; i++) {
            long temp = curr;
            // 计算新的当前值
            curr = prev + curr;
            prev = temp;
        }
        return curr;
    }

}
