package cn.cc.jdk.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Stack
 */
public class BracketChecker {
    public static void main(String[] args) {
        String str = "a{a})aa";
        System.out.println(isValid(str));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        // 建立括号对应关系（右括号为key）
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put(')', '(');
        brackets.put(']', '[');
        brackets.put('}', '{');

        for (char c : s.toCharArray()) {
            // 用于检查映射中是否存在特定的值
            if (brackets.containsValue(c)) { // 左括号入栈
                stack.push(c);
                // 检查Map集合对象中是否包含指定的键名
            } else if (brackets.containsKey(c)) { // 遇到右括号
                // 该元素从堆栈顶部弹出，并从堆栈中移除。
                if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty(); // 检查栈是否清空
    }
}

