package com.cc.sp91test.spcglib;

import com.cc.sp91test.test.spcglib.base.CglibProxy;
import com.cc.sp91test.test.spcglib.base.Host;
import com.cc.sp91test.test.spcglib.base.Rent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CglibProxyTests {

    @Test
    public void cglibProxy(){
        CglibProxy cglibProxy = new CglibProxy();
        Rent proxy = (Rent) cglibProxy.createProxy(new Host());
        proxy.rent();
    }


}
