package util;

import java.util.UUID;

public class UUIDTest {

    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
