/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-29 17:48
 * Copyright
 */

package t;

public class ForTests {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (i == 2) {
                continue;
            }
            System.out.println(i);
        }
    }

}
