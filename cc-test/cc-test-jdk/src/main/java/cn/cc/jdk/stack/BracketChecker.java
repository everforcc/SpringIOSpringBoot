package cn.cc.jdk.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
            if (brackets.containsValue(c)) { // 左括号入栈
                stack.push(c);
            } else if (brackets.containsKey(c)) { // 遇到右括号
                if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty(); // 检查栈是否清空
    }
}

