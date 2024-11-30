package org.apache.commons.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileUtilsTest {

    public static void main(String[] args) {
        try {
            /**
             * GBK
             * GB2312
             * Unicode
             */
            String result = FileUtils.readFileToString(new File("C:\\Users\\znkj\\Desktop\\spy.properties"), "GBK");
            log.info(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
