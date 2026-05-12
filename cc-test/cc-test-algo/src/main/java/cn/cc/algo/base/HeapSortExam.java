package cn.cc.algo.base;

import java.util.Arrays;

/**
 * 堆排序 Java 实现（完全还原 2022年下半年下午真题试题四逻辑）
 */
public class HeapSortExam {

    public static void main(String[] args) {
        // 初始序列：R[1...8]，R[0] 留空作为临时变量
//        int[] R = {0, 7, 10, 13, 15, 4, 20, 19, 8};
        int[] R = {0, 7, 10, 13, 15, 4, 20, 19, 8};
        int n = 8;

        System.out.println("原始序列: " + getArrayContent(R, n));

        heapSort(R, n);

        System.out.println("排序后序列: " + getArrayContent(R, n));
    }

    /**
     * 堆调整函数 (对应原题 Heapify)
     * @param R 存储数组
     * @param v 当前调整的节点编号
     * @param n 当前堆的规模
     */
    public static void heapify(int[] R, int v, int n) {
        int i, j;
        i = v;     // 当前节点索引
        j = 2 * i; // 左孩子索引
        R[0] = R[i]; // 对应原题逻辑：将父节点 R[i] 暂存在 R[0]

        while (j <= n) {
            // 如果有右孩子，且右孩子比左孩子大
            if (j < n && R[j] < R[j + 1]) {
                j++; // j 指向较大的孩子
            }

            // 要找出，当前节点和两个孩子结点中最大的那个
            // 【填空1】：如果较大的孩子比父节点大
            if (R[0] < R[j]) {
                R[i] = R[j]; // 孩子上移
                i = j;       // 记录新的空位索引
                j = 2 * i;   // 继续向下探测
            } else {
                j = n + 1;   // 对应原题 else 逻辑：强制跳出循环
            }
        }
        R[i] = R[0]; // 将暂存的父节点放入最终确定的空位
    }

    /**
     * 堆排序主函数 (对应原题 HeapSort)
     */
    public static void heapSort(int[] R, int n) {
        // 1. 构建初始大顶堆
        // 【填空2】：从最后一个非叶子节点 (n/2) 开始向上调整
        for (int i = n / 2; i >= 1; i--) {
            //
            heapify(R, i, n);
        }
        System.out.println("构建初始大顶堆后: " + getArrayContent(R, n));

        // 2. 交换并调整
        // 【填空3】：循环直到堆中只剩 1 个元素
        for (int i = n; i > 1; i--) {
            // 将堆顶 R[1]（最大值）与当前堆末尾 R[i] 交换
            int temp = R[i];
            R[i] = R[1];
            R[1] = temp;

            System.out.println("第 " + (9 - i) + " 次交换后（最大值 " + R[i] + " 归位）: " + getArrayContent(R, n));

            // 【填空4】：对新的堆顶进行调整，堆规模减 1
            heapify(R, 1, i - 1);
        }
    }

    // 辅助方法：打印 1...n 的内容
    private static String getArrayContent(int[] R, int n) {
        int[] content = new int[n];
        System.arraycopy(R, 1, content, 0, n);
        return Arrays.toString(content);
    }
}