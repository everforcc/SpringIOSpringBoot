package jdk.java.lottery;

import java.util.*;

/**
 * 双色球
 */
public class UnionLotto {
    public static void main(String[] args) {
        Random random = new Random();
        Set<Integer> redNumbers = new HashSet<>();

        // 生成不重复的6个红球号码（1-33）
        while (redNumbers.size() < 6) {
            int number = random.nextInt(33) + 1;
            redNumbers.add(number); // 利用Set自动去重
        }

        // 将红球号码排序
        List<Integer> sortedRedNumbers = new ArrayList<>(redNumbers);
        Collections.sort(sortedRedNumbers);

        // 生成蓝球号码（1-16）
        int blueNumber = random.nextInt(16) + 1;

        // 输出结果
        for (int num : sortedRedNumbers) {
            System.out.print(num + " ");
        }
        System.out.println(blueNumber);
    }
}
