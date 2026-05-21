package cn.cc.algo.rk.back;

import java.util.Arrays;

/**
 * N 皇后问题
 */
public class NQueensSolver {
    private int[] queen; // 索引代表行，值代表列。如 queen[0]=3 表示第0行皇后在第3列
    private int count = 0; // 记录总解数

    public void solve(int n) {
        this.queen = new int[n];
        backtrack(0, n); // 从第 0 行开始探测
        System.out.println(" 最终求解完成，总可行方案数: " + count);
    }

    /**
     * 回溯算法核心递归体
     *
     * @param row 当前正在处理的行
     * @param n   棋盘规模
     */
    private void backtrack(int row, int n) {
        // 【递归出口】：如果成功摆放了最后一行 (row == n)，说明找到了一组可行解
        if (row == n) {
            count++;
            printBoard(n);
            return;
        }

        // 尝试将当前行的皇后放在不同的列 (0 ~ n-1)
        for (int col = 0; col < n; col++) {
            // 【填空点 1】：判定在 (row, col) 放置是否与之前已放的皇后冲突
            if (isSafe(row, col)) {
                queen[row] = col; // 放置皇后 (占用资源)

                // 【填空点 2】：递归调用，处理下一行
                backtrack(row + 1, n);

                // 隐式回溯：循环继续，会尝试下一个 col，覆盖掉 queen[row] 的旧值
            }
        }
    }

    /**
     * 判定冲突的安全检查函数
     */
    private boolean isSafe(int row, int col) {
        for (int j = 0; j < row; j++) {
            // 1. 同列冲突：之前的某个皇后也在第 col 列
            // 2. 斜线冲突：横纵坐标差的绝对值相等 (对应原题核心数学公式)
            if (queen[j] == col || Math.abs(queen[j] - col) == Math.abs(j - row)) {
                return false; // 冲突，当前分支不安全
            }
        }
        return true; // 安全，可以放置
    }

    private void printBoard(int n) {
        System.out.println("方案 " + count + ": " + Arrays.toString(queen));
    }

    public static void main(String[] args) {
        NQueensSolver solver = new NQueensSolver();
        // 跑一个经典的 4 皇后问题 (输出 2 种方案)
        solver.solve(4);
    }
}
