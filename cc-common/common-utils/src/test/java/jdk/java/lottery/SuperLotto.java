package jdk.java.lottery;
import java.util.*;
public class SuperLotto {

    public static void main(String[] args) {
        Random random = new Random();

        // 前区号码：从 1~35 中选 5 个不重复数字
        List<Integer> frontNumbers = new ArrayList<>();
        while (frontNumbers.size() < 5) {
            int num = random.nextInt(35) + 1;
            if (!frontNumbers.contains(num)) {
                frontNumbers.add(num);
            }
        }

        // 后区号码：从 1~12 中选 2 个不重复数字
        List<Integer> backNumbers = new ArrayList<>();
        while (backNumbers.size() < 2) {
            int num = random.nextInt(12) + 1;
            if (!backNumbers.contains(num)) {
                backNumbers.add(num);
            }
        }

        Collections.sort(frontNumbers);
        Collections.sort(backNumbers);

        System.out.println("前区号码: " + formatNumbers(frontNumbers));
        System.out.println("后区号码: " + formatNumbers(backNumbers));
    }

    private static String formatNumbers(List<Integer> numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(String.format("%02d", numbers.get(i)));
        }
        return sb.toString();
    }

}
