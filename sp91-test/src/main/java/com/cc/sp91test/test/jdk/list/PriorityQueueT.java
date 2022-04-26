package com.cc.sp91test.test.jdk.list;

import java.util.*;

public class PriorityQueueT {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        PriorityQueue<Integer> priorityQueue =
                new PriorityQueue<>();
        Random rand = new Random(47);
        for(int i = 0; i < 10; i++)
            priorityQueue.offer(rand.nextInt(i + 10));
        System.out.println(priorityQueue);

        List<Integer> ints = Arrays.asList(25, 22, 20,
                18, 14, 9, 3, 1, 1, 2, 3, 9, 14, 18, 21, 23, 25);
        priorityQueue = new PriorityQueue<>(ints);
        System.out.println(priorityQueue);
        priorityQueue = new PriorityQueue<>(
                ints.size(), Collections.reverseOrder());
        priorityQueue.addAll(ints);
        System.out.println(priorityQueue);

        String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
        List<String> strings =
                Arrays.asList(fact.split(""));
        PriorityQueue<String> stringPQ =
                new PriorityQueue<>(strings);
        System.out.println(stringPQ);
        stringPQ = new PriorityQueue<>(
                strings.size(), Collections.reverseOrder());
        stringPQ.addAll(strings);
        System.out.println(stringPQ);

        Set<Character> charSet = new HashSet<>();
        for(char c : fact.toCharArray())
            charSet.add(c); // Autoboxing
        PriorityQueue<Character> characterPQ =
                new PriorityQueue<>(charSet);
        System.out.println(characterPQ);
    }

}
