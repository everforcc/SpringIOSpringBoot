package cn.cc.algo.rk.back;


/**
 * 0/1 背包问题 - 回溯法路径追踪版
 * 算法策略：回溯法 (Backtracking)
 * 逻辑：深度优先遍历解空间树
 */
public class KnapsackBacktrackingTrace {

    // 物品重量与价值
    private int[] weights = {7, 3, 4, 5};
    private int[] values = {42, 12, 40, 25};
    private int capacity = 10;

    private int maxValue = 0;
    private String bestPath = "";

    public static void main(String[] args) {
        KnapsackBacktrackingTrace solver = new KnapsackBacktrackingTrace();
        System.out.println(">>> 0/1 背包回溯搜索开始，总容量: " + solver.capacity);
        System.out.println("==================================================");

        solver.solve(0, 0, 0, "");

        System.out.println("==================================================");
        System.out.println(">>> 搜索结束。");
        System.out.println(">>> 全局最优价值: " + solver.maxValue);
        System.out.println(">>> 最优装入方案: " + solver.bestPath);
    }

    /**
     * 回溯核心递归函数
     *
     * @param index 当前决策的物品索引
     * @param curW  当前累计重量
     * @param curV  当前累计价值
     * @param path  记录决策路径的字符串
     */
    public void solve(int index, int curW, int curV, String path) {
        // 缩进显示递归深度
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append("  ");
        }
        String indent = sb.toString();

        // 1. 递归出口：所有物品已决策完毕
        if (index == weights.length) {
            System.out.println(indent + "🏁 到达叶子节点 | 最终方案: " + path + " | 总重: " + curW + " | 总价: " + curV);
            if (curV > maxValue) {
                maxValue = curV;
                bestPath = path;
                System.out.println(indent + "✨ 发现更优解! 更新全局最高价值为: " + maxValue);
            }
            return;
        }

        // 2. 分支一：尝试装入当前物品 (左子树)
        System.out.println(indent + "❓ 物品 " + (char) ('A' + index) + " (W:" + weights[index] + ", V:" + values[index] + ") 决策中...");

        if (curW + weights[index] <= capacity) {
            System.out.println(indent + "  ✅ [选它] 当前重量 " + (curW + weights[index]) + " <= " + capacity + "，递归向下");
            solve(index + 1, curW + weights[index], curV + values[index], path + (char) ('A' + index) + " ");
        } else {
            // 剪枝动作 (Pruning)
            System.out.println(indent + "  ❌ [不选它] 剪枝! 若选它重量将达 " + (curW + weights[index]) + "，超重了");
            // 注意：即使因为超重没选，为了保证搜索树完整，逻辑上这里仍需考虑“不选它”的右分支。
        }

        // 3. 分支二：尝试不装入当前物品 (右子树)
        System.out.println(indent + "  ⚪ [跳过] 物品 " + (char) ('A' + index) + "，保持重量 " + curW + "，递归向下");
        solve(index + 1, curW, curV, path);
    }
}