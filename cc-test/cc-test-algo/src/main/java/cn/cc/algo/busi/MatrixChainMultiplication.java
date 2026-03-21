package cn.cc.algo.busi;

public class MatrixChainMultiplication {
    public static void main(String[] args) {
        // 维度数组 P，对应你题目中的 P = [20,15,4,10,20,25]
        // P.length = n+1，n是矩阵个数（本题n=5）
        int[] P = {20, 15, 4, 10, 20, 25};
        int n = P.length - 1; // 矩阵个数：5

        // 1. 空间复杂度O(n²)的核心：创建n+1行n+1列的m表（下标从1开始更符合解题习惯）
        int[][] m = new int[n + 1][n + 1];

        // 初始化：长度为1的链（i=j），乘法次数为0（默认值就是0，可省略）
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        // 第一层循环：遍历链长 L（控制先算短链、再算长链）
        /**
         * cc
         * 因为要算长链，比如 m[1][4] = m[1][1] + m[2][4] + P[0]*P[1]*P[4]
         * 要知道  m[2][4] 的最小值是多少，然后他也要计算
         * 所以要先算短链，再算长链
         */
        // L表示当前计算的链包含的矩阵个数，从2到n
        for (int L = 2; L <= n; L++) {
            System.out.println("===== 计算链长 L = " + L + " 的矩阵链 =====");

            // 第二层循环：遍历起始位置 i
            /**
             * cc
             * n - L + 1 是因为 n是数组总长度，L是短链长度，比如第一次 n=5 L=2 那么i最后结束的位置就是4， 所以要 +1
             */
            // i的范围：1 <= i <= n-L+1，保证链的结束位置j = i+L-1 <= n
            for (int i = 1; i <= n - L + 1; i++) {
                /**
                 * cc
                 * 每次矩阵开始和结束的位置
                 */
                int j = i + L - 1; // 链的结束位置
                m[i][j] = Integer.MAX_VALUE; // 初始化最小值为无穷大
                System.out.println("  计算 m[" + i + "][" + j + "]（起始i=" + i + "，结束j=" + j + "）");

                // 第三层循环：遍历拆分点 k
                /**
                 * cc
                 * 当L=2的时候每次需要拆分1次
                 * 当L=3的时候每次需要拆分2次
                 * 当L=4的时候每次需要拆分3次
                 * 当L=5的时候每次需要拆分4次
                 */
                // k的范围：i <= k < j，尝试所有拆分方式
                for (int k = i; k < j; k++) {
                    // 核心公式：m[i][k] + m[k+1][j] + P[i-1]*P[k]*P[j]
                    int cost = m[i][k] + m[k + 1][j] + P[i - 1] * P[k] * P[j];
                    System.out.println("    拆分点k=" + k + "，总次数=" + cost);

                    // 更新最小值
                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                    }
                }
                System.out.println("  m[" + i + "][" + j + "] 的最小值 = " + m[i][j] + "\n");
            }
        }

        // 输出最终结果：5个矩阵的最少乘法次数
        System.out.println("最终答案：A1-A5的最少乘法次数 = " + m[1][5]);
    }
}
