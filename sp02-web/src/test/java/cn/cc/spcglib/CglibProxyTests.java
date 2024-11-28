package cn.cc.spcglib;

import cn.cc.spcglib.base.CglibProxy;
import cn.cc.spcglib.base.Host;
import cn.cc.spcglib.base.Rent;
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