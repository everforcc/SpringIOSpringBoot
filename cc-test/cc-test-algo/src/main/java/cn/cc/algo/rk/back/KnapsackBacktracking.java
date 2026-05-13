package cn.cc.algo.rk.back;

public class KnapsackBacktracking {

    public static void main(String[] args) {
        KnapsackBacktracking solver = new KnapsackBacktracking();
        solver.solve(0, 0, 0);
        System.out.println(">>> 搜索结束。");
        System.out.println(">>> 全局最优价值: " + solver.maxValue);
//        System.out.println(">>> 最优装入方案: " + solver.bestPath);
    }

    private int[] weights = {7, 3, 4, 5};
    private int[] values = {42, 12, 40, 25};
    private int capacity = 10;
    private int maxValue = 0;

    public void solve(int index, int currentWeight, int currentValue) {
        // 1. 终止条件
        if (index == weights.length) {
            maxValue = Math.max(maxValue, currentValue);
            return;
        }

        // 2. 选择放入物品（左递归）
        if (currentWeight + weights[index] <= capacity) {
            solve(index + 1, currentWeight + weights[index], currentValue + values[index]);
        }

        // 3. 选择不放物品（右递归，回溯点）
        solve(index + 1, currentWeight, currentValue);
    }

}