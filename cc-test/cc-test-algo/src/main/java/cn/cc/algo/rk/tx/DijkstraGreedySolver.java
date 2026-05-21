package cn.cc.algo.rk.tx;

/**
 * Dijkstra 最短路径算法
 */
public class DijkstraGreedySolver {
    private static final int INF = Integer.MAX_VALUE; // 代表正无穷（无边相连）

    /**
     * Dijkstra 算法的 Java 1:1 严谨复刻版
     *
     * @param n      顶点数量
     * @param start  起点（源点）
     * @param weight 邻接矩阵（存储边的权重）
     */
    public void solveDijkstra(int n, int start, int[][] weight) {
        int[] dist = new int[n];       // 暂存表（账本）：记录源点到各点的当前预估距离
        int[] selected = new int[n];   // 标记表（队伍）：1代表“已确定队”，0代表“临时排队队”

        // 1. 初始化
        for (int i = 0; i < n; i++) {
            dist[i] = weight[start][i]; // 初始账本：直接抄源点到各点的直连距离
            selected[i] = 0; // 初始时，所有点都在“临时排队”
        }
        dist[start] = 0; // 起点到自己的距离为 0
        selected[start] = 1; // 起点直接拉入“已确定队”

        // 2. 主循环：依次确定剩余 n-1 个顶点的最短路径
        for (int i = 1; i < n; i++) {
            int min = INF;
            int u = -1;

            // 【第一步：贪心选择】（对应原题空 1）
            // 在未选中的节点中，寻找当前距离起点最近的节点 u
            for (int j = 0; j < n; j++) {
                // 条件：必须在“临时排队队”（selected[j] == 0），且距离 min 是当前最小的
                if (selected[j] == 0 && dist[j] < min) {
                    min = dist[j]; // 记录当前最小值
                    u = j;         // 记录这个最近节点的编号
                }
            }

            if (u == -1) {
                break; // 剩余节点均不可达，跳出
            }

            // 【第二步：标记落座】（对应原题空 2）
            selected[u] = 1; // 物理锁死：将 u 从“临时排队队”剪切到“已确定队”

            // 【第三步：松弛操作（放松约束）】（对应原题空 3、4）
            // 借助刚刚加入的节点 u，尝试缩短起点到其他未确定节点 j 的距离
            for (int j = 0; j < n; j++) {
                // 条件 1：只针对还没锁定的节点 j 进行更新
                // 条件 2：新锁定的 u 与 j 之间必须有路（weight[u][j] < INF）
                if (selected[j] == 0 && weight[u][j] < INF) {
                    // 核心判定：从起点到 u（已锁死）再到 j 的总距离，是否比之前账本上记录的直连距离更短？
                    // 如果通过 u 中转的距离比原先记录的距离更短，则更新
                    if (dist[u] + weight[u][j] < dist[j]) { // 对应空(3)的逻辑
                        dist[j] = dist[u] + weight[u][j];  // 对应空(4)的逻辑
                    }
                }
            }
        }

        printResult(start, dist);
    }

    private void printResult(int start, int[] dist) {
        System.out.println("源点 " + start + " 到各顶点的最短路径距离:");
        for (int i = 0; i < dist.length; i++) {
            String val = dist[i] == INF ? "INF" : String.valueOf(dist[i]);
            System.out.println("-> 顶点 " + i + " : " + val);
        }
    }

    public static void main(String[] args) {
        DijkstraGreedySolver solver = new DijkstraGreedySolver();
        // 构造一个 3 个节点的有向图
        int[][] graph = {
                {0, 5, INF},
                {INF, 0, 2},
                {3, INF, 0}
        };
        solver.solveDijkstra(3, 0, graph);
    }
}