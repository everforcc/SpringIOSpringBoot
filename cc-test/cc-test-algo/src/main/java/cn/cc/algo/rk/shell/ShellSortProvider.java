package cn.cc.algo.rk.shell;


import java.util.Arrays;

/**
 * 希尔排序（Shell Sort）- 2020H2 软考真题复刻版
 * 核心策略：缩小增量排序（Diminishing Increment Sort）
 * <p>
 * 比如就这个序列举例 {9, 8, 7, 6, 5, 4, 3, 2, 1}
 * n=4
 * 保证 arr = (0,4)(1,5)(2,6)(3,7)(4,8) 有序
 * n=2 假如 arr[0]<arr[2] 就不用比较了
 * 是因为 要比较的序列是 0,2,4,6,8
 * 本来就满足 arr[0]<arr[4]<arr[8]
 * 假如 arr[0]<arr[2]
 * 怎么能推导出 arr[2]，arr[6]和 arr[4] 的关系
 * <p>
 * n=1
 *
 */
public class ShellSortProvider {

    public void ShellSort(int[] data, int n) {
        // 严格复刻原题变量声明
        int[] delta;
        int k, i, t, dk, j;

        k = n;
        // 一比一复刻 C 语言的 malloc 逻辑，Java 中使用 new 分配固定长度数组
        delta = new int[n / 2];
        i = 0;

        // 【步骤 1】构造步长序列
        // 思想：通过不断折半，生成一个递减的步长序列，如：4, 2, 1
        System.out.println("delta: " + k);
        do {
            k = k / 2;          // 对应填空 (1)
            System.out.println("delta: " + k);
            delta[i++] = k;
        } while (k > 1);        // 对应填空 (2)

        i = 0;

        /**
         *  第一层 步长控制器 (Gap Controller)
         * 【步骤 2 & 3】根据步长序列进行排序
         * 确认退出条件，处理所有的步长
         * 这里的 while 循环条件在 C 语言中通常判断 delta[i] 是否大于 0
         */
        while (i < delta.length && delta[i] > 0) {
            System.out.println(" >>>>>>>>>>>>>>>>>>");
            dk = delta[i];
            System.out.println("步长 dk: " + dk);
            // 跨步长的直接插入排序

            /**
             * 【第二层：for 循环】—— 逻辑组扫描器 (Subsequence Scanner)
             * 作用：从当前步长位置开始，向后遍历数组中的每一个元素。
             * 确保每一个逻辑子序列（如：0,2,4,6...）都能被扫描到。
             */
            for (k = dk; k < n; ++k) {// 这个会从前先后扫描所有的位置
                // 对应填空 (3)：判断当前元素是否小于前一个等步长元素
                System.out.println("开始位置 k: " + k);
                System.out.println("data[k]: " + data[k]);
                System.out.println("data[k - dk]: " + data[k - dk]);
                System.out.println("-----------");
                // 由于插入排序的前提是“前面的序列已经局部有序”，如果它比前一个大，它就一定比前面所有的都大。

                /**
                 * 【第三层：if 判断】—— 插入触发开关 (Insertion Trigger)
                 * 作用：执行“快速失败”检查。判定当前元素是否比它组内的前驱小。
                 * 若为 false，说明当前元素在组内已局部有序，直接跳过，保护 CPU 缓存。
                 */
                if (data[k] < data[k - dk]) { // 第一次也只比较两个的大小
                    t = data[k];
                    System.out.println("临时存值 t: " + t);


                    /**
                     * 【第四层：for 循环】—— 组内腾挪器 (Logical Shifter)
                     * 作用：在当前逻辑子序列中，将所有比 t 大的元素依次后移 dk 位。
                     * 类似于 Java ArrayList.add 时的数组后移，为新元素腾出物理坑位。
                     */
                    for (j = k - dk; //
                         j >= 0 && t < data[j]; //
                         j -= dk) { // 从后向前分析，为本地比较的数据确认位置

                        System.out.println("data[j + dk]: " + data[j + dk]);
                        System.out.println("data[j]: " + data[j]);

                        data[j + dk] = data[j];

                        System.out.println("data[j + dk]: " + data[j + dk]);
                        System.out.println("data[j]: " + data[j]);
                        System.out.println("排序后序列: " + Arrays.toString(data));
                    }

                    // 对应填空 (4)：插入元素
                    data[j + dk] = t;
                    System.out.println("data[j + dk]: " + data[j + dk]);
                    System.out.println("排序后序列: " + Arrays.toString(data));
                }
                System.out.println("-----------");
            }
            ++i;
            System.out.println(" >>>>>>>>>>>>>>>>>>");
        }
    }

    // 验证方法
    public static void main(String[] args) {
        ShellSortProvider sorter = new ShellSortProvider();
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int n = 9;
        System.out.println("排序前序列: " + Arrays.toString(arr));
        sorter.ShellSort(arr, n);

        // 输出结果：[-1, 4, 7, 8, 9, 15, 20]
        System.out.println(java.util.Arrays.toString(arr));
    }
}


