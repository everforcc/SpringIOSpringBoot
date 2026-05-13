package cn.cc.algo.rk;

import java.util.Arrays;

/**
 * 快速排序实现（全过程追踪版）
 * 详细记录每一次比较与交换动作
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {9, 38, 66, 90, 88, 10, 25};
//        int[] arr = {45, 38, 66, 90, 88, 10, 25};
        System.out.println("初始状态: " + Arrays.toString(arr));
        System.out.println("==================================================");

        quickSort(arr, 0, arr.length - 1);

        System.out.println("==================================================");
        System.out.println("最终排序结果: " + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            System.out.println("处理左区间 --- low " + low + " high" + (pivotIndex - 1) + " start");
            quickSort(arr, low, pivotIndex - 1);
            System.out.println("处理左区间 --- low " + low + " high" + (pivotIndex - 1) + " end");
            System.out.println("处理右区间 --- low " + (pivotIndex + 1) + " high" + high + " start");
            quickSort(arr, pivotIndex + 1, high);
            System.out.println("处理右区间 --- low " + (pivotIndex + 1) + " high" + high + " end");
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        System.out.println("[分区开始] 区间: [" + low + "," + high + "] | 基准值: " + pivot);

        for (int j = low; j < high; j++) {
            System.out.print("  比较: arr[" + j + "]=" + arr[j] + " 与 基准值=" + pivot);
            if (arr[j] < pivot) {
                i++;
                System.out.println(" -> [符合] 小于基准值");
                if (i != j) {
                    System.out.println("    执行交换: arr[" + i + "]=" + arr[i] + " <-> arr[" + j + "]=" + arr[j]);
                    swap(arr, i, j);
                    System.out.println("    当前数组: " + Arrays.toString(arr));
                } else {
                    System.out.println("    索引相同，无需物理交换");
                }
            } else {
                System.out.println(" -> [跳过] 不小于基准值");
            }
        }

        // 最终基准值归位
        System.out.println("  [归位交换]: 基准值 " + pivot + " 移至索引 " + (i + 1));
        swap(arr, i + 1, high);
        System.out.println("[分区结束] 调整后: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------");

        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}