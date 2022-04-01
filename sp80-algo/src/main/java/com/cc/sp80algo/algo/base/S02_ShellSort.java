package com.cc.sp80algo.algo.base;

/**
 * 02. 希尔排序
 *
 * 先保证后半比前半都大
 */
public class S02_ShellSort {

    //核心代码---开始
    public static void sort(Comparable[] arr) {

        int j;

        for (int gap = arr.length / 2; gap >  0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                Comparable tmp = arr[i];
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
        }
    }
    //核心代码---结束
    public static void main(String[] args) {

        int N = 20;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 10);

        sort(arr);

        for( int i = 0 ; i < arr.length ; i ++ ){
            System.out.print(arr[i]);
            System.out.print(' ');
        }
    }

}
