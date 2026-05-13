package cn.cc.algo.rk;
import java.util.Arrays;

/**
 * 装箱问题（Bin Packing Problem）算法实现
 * 策略：最先适宜策略 (First Fit) 与 最优适宜策略 (Best Fit)
 */
public class BinPackingProvider {

    // 货物数
    private int n;
    // 集装箱容量
    private int C;
    // 货物体积数组
    private int[] s;
    // 集装箱当前负载数组
    private int[] b;
    // 所需集装箱总数
    private int k;

    public BinPackingProvider(int n, int C, int[] s) {
        this.n = n;
        this.C = C;
        this.s = s;
        this.b = new int[n]; // 最坏情况每个货物占一个箱子
        this.k = 0;
    }

    /**
     * 最先适宜策略 (First Fit)
     * 逻辑：每次将货物装入第一个能容纳它的集装箱中。
     * 时间复杂度：O(n^2)
     */
    public int firstFit() {
        k = 0;
//        Arrays.fill(b, 0);
        for (int i = 0; i < n; i++) {
            b[i] = 0; // 初始化所有集装箱负载为 0
        }

        for (int i = 0; i < n; i++) {
            // (1) 填空：从第一个集装箱开始扫描
            int j = 0;

            // 寻找第一个能容纳当前货物 s[i] 的集装箱
            while (C - b[j] < s[i]) {
                j++;
            }

            // (2) 填空：更新集装箱负载
            b[j] = b[j] + s[i];

            // 更新已使用的集装箱总数
            if (k < (j + 1)) {
                k = j + 1;
            }
        }
        return k;
    }

    /**
     * 最优适宜策略 (Best Fit)
     * 逻辑：将货物装入能容纳它且装入后剩余空间最小的集装箱。
     * 时间复杂度：O(n^2)
     */
    public int bestFit() {
        k = 0;
//        Arrays.fill(b, 0);
        for (int i = 0; i < n; i++) {
            b[i] = 0; // 初始化所有集装箱负载为 0
        }
        for (int i = 0; i < n; i++) {
            int min = C;
            int m = k; // 默认指向新箱子索引

            // 遍历已开启的箱子寻找最优坑位
            for (int j = 0; j < k; j++) {
                int temp = C - b[j] - s[i];
                // (3) 填空：如果当前方案剩余空间更小，则更新最优目标
                if (temp >= 0 && temp < min) {
                    min = temp;
                    m = j;
                }
            }

            // (4) 填空：执行装箱
            b[m] = b[m] + s[i];

            // 更新已使用的集装箱总数
            if (k < (m + 1)) {
                k = m + 1;
            }
        }
        return k;
    }

    public static void main(String[] args) {
        // 实例数据：n=10, C=10, s={4, 2, 7, 3, 5, 4, 2, 3, 6, 2}
        int[] itemSizes = {4, 2, 7, 3, 5, 4, 2, 3, 6, 2};
        BinPackingProvider provider = new BinPackingProvider(10, 10, itemSizes);

        System.out.println("最先适宜策略 (First Fit) 所需箱数: " + provider.firstFit());
        System.out.println("最优适宜策略 (Best Fit) 所需箱数: " + provider.bestFit());
    }
}