package algo.busi;

public class Stupid {

    public static void main(String[] args) {
        int[] ints = new int[]{1,2,4,5,8,3};
        sleepSort(ints);
    }

    public static void sleepSort(int[] nums){
        int size = nums.length;
        for(int i=0;i<size;i++){
            final  int num = nums[i];
            new Thread(() -> {
                try {
                    Thread.sleep(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(num);
            }).start();
        }
    }

}
