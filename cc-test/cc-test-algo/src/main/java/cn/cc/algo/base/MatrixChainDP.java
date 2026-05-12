package cn.cc.algo.base;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 矩阵连乘问题 - 动态规划过程追踪版
 * 对应 2022年上半年下午真题试题四逻辑
 */
@Slf4j
public class MatrixChainDP {

    private static final int N = 100;
    private static int[][] cost = new int[N][N];
    private static int[][] trace = new int[N][N];

    public static void main(String[] args) {
        // 矩阵维数序列：A1(15x5), A2(5x10), A3(10x20), A4(20x25)
        int[] seq = {15, 5, 10, 20, 25};
        int n = 4; // 矩阵个数

        System.out.println(">>> 算法开始执行，矩阵序列长度: " + n);
        System.out.println(">>> 维数序列 (seq): " + Arrays.toString(seq));
        System.out.println("==================================================");

        int minCost = cmm(n, seq);

        System.out.println("==================================================");
        System.out.println(">>> 最终最小乘法运算次数: " + minCost);
        System.out.print(">>> 最优计算顺序: ");
        printOptimalParentheses(0, n - 1);
        System.out.println();
    }

    public static int cmm(int n, int[] seq) {
        int i, j, k, p;
        int temp;

        // 1. 初始化：单个矩阵相乘代价为 0
        for (i = 0; i < n; i++) {
            cost[i][i] = 0;
        }
        System.out.println("[步骤1] 初始化对角线 cost[i][i] = 0 (单个矩阵无乘法开销)");

        // 2. p 为连乘链的长度（从长度 1 到 n-1）
        // ** 变量p的严谨定义是索引偏移量**
        // 可以把p理解为矩阵之间的**“乘号”数量**
        for (p = 1; p < n; p++) { // todo 长度1但是却包含2个序列，所以 p 不能等于 n
            System.out.println("\n[长度 p = " + p + "] 正在计算所有包含 " + (p + 1) + " 个矩阵的子链:");

            // i 为连乘链的起点

            /**
             * 当 p = 1 时，
             * 比如数组长度为5，矩阵个数为4， 子链就分为这几个 (A1,A2),(A2,A3),(A3,A4)
             *
             * 下面的条件分析
             * 这就是i 自增的原因
             * j是终点，因为i是起点，p是连乘的长度，那么 j=i+p 就是终点
             * i < n-p , i+p 肯定要小于n，为什么不等于n，因为计算的时候，矩阵
             */

            /**
             * 在 0 阶索引体系下，i 的取值范围从 0 开始。
             * 矩阵总数为 n，最大合法索引为 n-1
             * 当前子链的终点索引定义为 j=i+p,起点加上偏移量，就是终点
             * 推导
             *  j ≤ n−1
             *  i+p ≤ n−1
             *  i < n - p
             */
            for (i = 0; i < n - p; i++) { // i的含义 起点
                j = i + p; // j 的含义终点 = p是长度 + i起点
                int tempCost = -1;

                System.out.println("  正在评估子链 A" + (i + 1) + " 到 A" + (j + 1) + ":");

                // k 为切分点
                // todo step n 根据定义 cost[i][k]是从 i+1 个矩阵到 k+1 个矩阵
                // k < j 是因为终点是j，根据定义右侧等于 cost[k+1][j]，所以 k < j
                for (k = i; k < j; k++) {

                    // 公式内容
                    // 状态转移方程：左子链代价 + 右子链代价 + 合并代价
                    int leftPart = cost[i][k];
                    int rightPart = cost[k + 1][j];
                    int mergePart = seq[i] * seq[k + 1] * seq[j + 1];
                    temp = leftPart + rightPart + mergePart;
                    // 几个参数都是题干定义
                    // 上面和下面的
                    // k+1 是切分点
                    // i+1和j+1是两个矩阵的维数
                    // j+1是矩阵的维数
                    System.out.print("    尝试在 k=" + (k + 1) + " 处切分: (" + (i + 1) + ".." + (k + 1) + ") * (" + (k + 2) + ".." + (j + 1) + ")");
                    System.out.println(" -> 代价: " + leftPart + " + " + rightPart + " + " + mergePart + " = " + temp);

                    System.out.printf("cost[%s][%s] = %s \r\n", i, k, leftPart);
                    System.out.println("--------------");
                    System.out.printf("cost[%s][%s] = %s \n", k + 1, j, rightPart);
                    System.out.println("--------------");
                    System.out.printf("seq[%s] * seq[%s] * seq[%s] = %s \n", i, k + 1, j + 1, mergePart);
                    System.out.println("--------------");

                    if (tempCost == -1 || tempCost > temp) {
                        tempCost = temp;
                        trace[i][j] = k;
                        System.out.println("    *** 发现更优切分点! 当前最小代价更新为: " + tempCost);
                    }
                }
                cost[i][j] = tempCost;
                System.out.println("  >> 子链 A" + (i + 1) + "..A" + (j + 1) + " 的最优解已存入表: " + cost[i][j]);
            }
        }
        return cost[0][n - 1];
    }

    private static void printOptimalParentheses(int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1));
        } else {
            System.out.print("(");
            printOptimalParentheses(i, trace[i][j]);
            System.out.print("*");
            printOptimalParentheses(trace[i][j] + 1, j);
            System.out.print(")");
        }
    }
}