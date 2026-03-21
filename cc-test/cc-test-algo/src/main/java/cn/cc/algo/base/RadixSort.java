package cn.cc.algo.base;

import java.util.Arrays;

/**
 * 基数排序（LSD 低位优先）
 * 核心：按个位、十位、百位...依次排序，每一位用计数排序保证稳定性
 *
 * 基数排序中计数排序（按位）稳定排序流程总结
 * 1. 统计次数：遍历原数组，统计当前排序位（个位 / 十位 / 百位）每个数字（0-9）的出现次数，存入 count 数组；
 * 2. 前缀和定位：计算 count 数组的前缀和，将 “次数统计” 转为 “≤当前数字的元素总数”，确定每个数字在输出数组中的最后位置 + 1；
 * 3. 倒序回填：倒序遍历原数组，对每个元素：
 *  3.1. 提取当前位数字，用count[digit]-1确定其在输出数组的位置；
 *  3.2. 放入元素后，将count[digit]减 1（为下一个同数字元素预留前一位）；
 * 4. 结果回写：将输出数组复制回原数组，完成当前位排序；
 * 5. 循环处理：按位（个位→十位→百位）重复上述步骤，直到所有位排序完成。
 *
 * 核心关键（稳定性 + 定位）
 * 1. 前缀和：给每个数字划定输出数组的 “位置区间”，避免越界 / 重叠；
 * 2. 倒序 + count 减 1：保证相同数字的元素相对顺序不变，实现稳定排序。
 *
 */
public class RadixSort {

    /**
     * 基数排序主方法
     * @param arr 待排序的整数数组（支持正整数，如需负数可先偏移）
     */
    public static void radixSort(int[] arr) {
        // 空数组或单元素数组无需排序
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 步骤1：找到数组中的最大值，确定需要排序的最大位数（如999是3位）
        int max = findMax(arr);
        // 步骤2：按位排序（个位→十位→百位...），divisor=1(个位)、10(十位)、100(百位)...
        for (int divisor = 1; max / divisor > 0; divisor *= 10) {
            // 对当前位进行计数排序（核心：保证稳定性）
            countingSortByDigit(arr, divisor);
        }
    }

    /**
     * 找到数组中的最大值
     */
    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    /**
     * 按指定位（个位/十位/百位...）进行计数排序（保证稳定性）
     * @param arr 待排序数组
     * @param divisor 位因子：1(个位)、10(十位)、100(百位)...
     */
    private static void countingSortByDigit(int[] arr, int divisor) {
        int n = arr.length;
        // 步骤1：准备输出数组（存储按当前位排序后的结果）
        int[] output = new int[n];
        // 步骤2：计数数组（十进制每一位的取值范围是0-9，长度固定为10）
        int[] count = new int[10];
        Arrays.fill(count, 0); // 初始化为0

        // 步骤3：统计当前位每个数字（0-9）出现的次数
        for (int num : arr) {
            // 提取当前位的数字：(num / divisor) % 10
            // 取出个位数的余数
            int digit = (num / divisor) % 10;
            // 统计每个个位出现的次数
            count[digit]++;
        }

        // 步骤4：计算前缀和（确定每个数字在output中的最后位置，保证稳定性）
        for (int i = 1; i < 10; i++) {
            // 从小到大排序的情况下，统计一下个位为0的有几个，排第几位到第几位，然后个位为1的有几个，从第几位到第几位
            count[i] += count[i - 1];
        }

        // 步骤5：倒序遍历原数组，将元素放入output的对应位置（核心：保证稳定性）
        // 倒序遍历 → 相同位的元素，原数组中靠后的仍靠后
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / divisor) % 10;
            // count[digit]-1 是当前元素的目标下标
            output[count[digit] - 1] = arr[i];
            count[digit]--; // 该数字的位置向前挪一位
        }

        // 步骤6：将排序后的结果复制回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

    // 测试用例
    public static void main(String[] args) {
        // 测试用例1：常规正整数数组
        int[] arr1 = {53, 3, 542, 748, 14, 214, 154, 63, 616};
        System.out.println("排序前：" + Arrays.toString(arr1));
        radixSort(arr1);
        System.out.println("排序后：" + Arrays.toString(arr1));

        // 测试用例2：包含重复元素的数组
        int[] arr2 = {123, 45, 123, 789, 45, 0, 99};
        System.out.println("\n排序前：" + Arrays.toString(arr2));
        radixSort(arr2);
        System.out.println("排序后：" + Arrays.toString(arr2));

        // 测试用例3：单元素/空数组
        int[] arr3 = {10};
        radixSort(arr3);
        System.out.println("\n单元素数组排序后：" + Arrays.toString(arr3));

        int[] arr4 = {};
        radixSort(arr4);
        System.out.println("空数组排序后：" + Arrays.toString(arr4));
    }
}