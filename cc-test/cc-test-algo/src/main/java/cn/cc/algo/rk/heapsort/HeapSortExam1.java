package cn.cc.algo.rk.heapsort;

import java.util.Arrays;

public class HeapSortExam1 {

    public static void main(String[] args) {
        int[] R = {0, 12, 23, 343, 5456, 57, 68, 67, 98};
        int n = R.length - 1;
        System.out.println("原始序列: " + Arrays.toString(R));
        heapSort(R, n);
        System.out.println("排序后序列: " + Arrays.toString(R));
    }


    /**
     * 构建堆
     *
     * @param R 数组
     * @param v 当前节点
     * @param n 堆规模
     */
    public static void heapify(int[] R, int v, int n) {
        int i, j;
        i = v;
        j = 2 * i; // 左孩子索引
        R[0] = R[i];

        // 每次循环，将当前节点 v 往下调整
        while (j <= n) {
            // 找出左右孩子中较大的那个
            if (j < n && R[j] < R[j + 1]) {
                j++;
            }

            // 将孩子中最大的那个和当前节点比较
            // 如果子节点中某个最大，那么再递归处理下级节点
            // 如果父节点最大，else直接退出，因为子节点的堆已构建完成
            if (R[0] < R[j]) {
                R[i] = R[j];
                i = j;
                j = 2 * i;
            } else {
                j = n + 1;
            }
        }
        R[i] = R[0];
    }

    /**
     * 堆排序
     *
     * @param R 数组
     * @param n 堆规模
     */
    public static void heapSort(int[] R, int n) {
        int i;
        int j;
        // step 1
        // 构建大顶堆
        for (i = n / 2; i > 0; i--) {
            heapify(R, i, n);
        }
        // step 2
        // 提取最大值
        for (i = n; i > 1; i--) {
            R[0] = R[i];
            R[i] = R[1];
            R[1] = R[0];
            heapify(R, 1, i - 1);
        }
    }

}
