package cn.cc.algo.rk.shell;


/**
 * 希尔排序（Shell Sort）- 2020H2 软考真题复刻版
 * 核心策略：缩小增量排序（Diminishing Increment Sort）
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
        do {
            k = k / 2;          // 对应填空 (1)
            delta[i++] = k;
        } while (k > 1);        // 对应填空 (2)

        i = 0;
        // 【步骤 2 & 3】根据步长序列进行排序
        // 这里的 while 循环条件在 C 语言中通常判断 delta[i] 是否大于 0
        while (i < delta.length && delta[i] > 0) {
            dk = delta[i];

            // 跨步长的直接插入排序
            for (k = dk; k < n; ++k) {
                // 对应填空 (3)：判断当前元素是否小于前一个等步长元素
                if (data[k] < data[k - dk]) {
                    t = data[k];

                    // 元素后移逻辑
                    for (j = k - dk; j >= 0 && t < data[j]; j -= dk) {
                        data[j + dk] = data[j];
                    }

                    // 对应填空 (4)：插入元素
                    data[j + dk] = t;
                }
            }
            ++i;
        }
    }

    // 验证方法
    public static void main(String[] args) {
        ShellSortProvider sorter = new ShellSortProvider();
        int[] arr = {15, 9, 7, 8, 20, -1, 4};
        int n = 7;

        sorter.ShellSort(arr, n);

        // 输出结果：[-1, 4, 7, 8, 9, 15, 20]
        System.out.println(java.util.Arrays.toString(arr));
    }
}


