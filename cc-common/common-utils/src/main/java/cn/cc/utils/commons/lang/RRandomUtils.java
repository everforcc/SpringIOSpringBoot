package cn.cc.utils.commons.lang;

public class RRandomUtils {

    public static int randomInt(Integer start, Integer end){
        //start = start - end;
        return org.apache.commons.lang3.RandomUtils.nextInt(start, end);
    }

    public static double getRandomDouble(double start,double end){
        return org.apache.commons.lang3.RandomUtils.nextDouble(start,end);
    }

    public static double getRandomFloat(float start,float end){
        return org.apache.commons.lang3.RandomUtils.nextFloat(start,end);
    }

    public static void main(String[] args) {
        //System.out.println(getRandomDouble(0.000d,0.004d-0));
        System.out.println(getRandomFloat(0.00f,1.00f-0));
    }
}
