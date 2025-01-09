package jdk.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class Tests {

    @Test
    public void mapTest() {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> map_1 = new HashMap<>();
        map_1.put("bb", "cc");
        map_1.put("dd", "cc");
        map_1.put("ee", "cc");
        map.put("aa", map_1);
        log.info("---s");
        Map<String, String> map1 = map.get("aa");
        if (Objects.nonNull(map1)) {
            Iterator<Map.Entry<String, String>>  iterator = map1.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                if("ee".equals(entry.getKey())){
                    log.info("remove");
                    iterator.remove();
                }
            }

//            map1.forEach((k, v) -> {
//                log.info(v);
//                if("ee".equals(k)){
//                    map1.remove(k);
//                }
//            });
        }
        log.info("map1: {}", map1.size());
        String removebb = map1.remove("bb");
        log.info("---e: {}", removebb);
        String removeDDD = map1.remove("ddd");
        log.info("---e: {}", removeDDD);
    }

}
