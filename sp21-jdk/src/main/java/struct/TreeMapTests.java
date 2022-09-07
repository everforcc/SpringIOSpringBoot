/**
 * @Description
 * @Author everforcc
 * @Date 2022-09-07 10:50
 * Copyright
 */

package struct;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTests {

    public static void main(String[] args) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("c", "c");
        treeMap.put("a", "a");
        treeMap.put("b", "b");
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
        }
    }

}
