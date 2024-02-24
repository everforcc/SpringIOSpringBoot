/**
 * @Description
 * @Author everforcc
 * @Date 2023-06-01 18:05
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import cn.cc.sp31usercraw.dodo.DownDo;
import org.junit.Test;

public class DownUtilsTest {

    @Test
    public void tDown() {
//        List<String> stringList = new ArrayList<>();
//        stringList.add("a");
//        stringList.add("b");
//        stringList.add("c");
//        stringList.add("d");
//        System.out.println(stringList.size());
//        System.out.println(stringList.get(0));
//        System.out.println(stringList.remove(0));
//        stringList.add("e");
//        System.out.println(stringList.get(0));
//        System.out.println(stringList.remove(0));
//        System.out.println(stringList.contains("c"));

        for (int i = 0; i < 10; i++) {
            DownUtils downUtils_a = DownUtils.instantce("a", "E:/");
            //DownUtils downUtils_b = DownUtils.instantce("b");
            DownUtils downUtils_a_1 = DownUtils.instantce("a", "E:/");
            DownDo downDo = new DownDo();
            downDo.setPath("abc");
            downUtils_a.add(downDo);
            downUtils_a.add(downDo);
            downUtils_a.add(downDo);
            downUtils_a.add(downDo);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("markAddEnd()");
            downUtils_a.markAddEnd();
        }

//        List<DownUtils> downUtilsList = new ArrayList<>();
//        downUtilsList.add(downUtils_a);
//        System.out.println(downUtilsList.contains(downUtils_a));
//        System.out.println(downUtilsList.contains(downUtils_b));
//        System.out.println(downUtilsList.contains(downUtils_a_1));
//
//        downUtilsList.add(downUtils_b);
//        System.out.println(downUtilsList.contains(downUtils_a));
//        System.out.println(downUtilsList.contains(downUtils_b));
//        System.out.println(downUtilsList.contains(downUtils_a_1));

    }

}
