package algo.base;

/**
 *  01. 插入排序
 */
public class S01_InsertionSort {

    //核心代码---开始
    public static void sort(Comparable[] arr){

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            // 寻找元素 arr[i] 合适的插入位置
            for( int j = i ; j > 0 ; j -- )
                if( arr[j].compareTo( arr[j-1] ) < 0 ) {
                    swap(arr, j, j - 1);
                }else {
                    break;
                }
        }

    }
    //核心代码---结束

    /**
     *  如果后一位，小于前一位 那么交换位置
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

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
