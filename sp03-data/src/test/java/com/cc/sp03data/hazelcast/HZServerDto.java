/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-01 16:59
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * hz服务
 * 对象
 */
@AllArgsConstructor
@NoArgsConstructor
public class HZServerDto {

    /**
     * hz属于哪个环境
     * localhost
     * txy
     */
    @Getter
    private String hzEnviroment;

    /**
     * ip:port 集合
     */
    @Getter
    private List<String> addressList;

    /**
     * 集群名,不能调整
     */
    @Getter
    private String clusterName;

    /**
     * 测试map的key
     */
    @Getter
    @Setter
    private String mapKey;


    public static HZServerDto getLocalInstance() {
        String localIP = "127.0.0.1";
        HZServerDto hzServerDto = new HZServerDto();
        hzServerDto.clusterName = "local";
        hzServerDto.clusterName = "hello-world";
        hzServerDto.setMapKey("map-local-str");
        hzServerDto.addressList = (new ArrayList<String>() {{
            add(localIP + ":5701");
            add(localIP + ":5702");
            add(localIP + ":5703");
        }});
        return hzServerDto;
    }

    public static HZServerDto getTXYInstance() {
        String txyIP = "43.143.228.164";
        HZServerDto hzServerDto = new HZServerDto();
        hzServerDto.clusterName = "txy";
        hzServerDto.clusterName = "txy-hello-world";
        hzServerDto.setMapKey("map-txy-str");
        hzServerDto.addressList = (new ArrayList<String>() {{
            add(txyIP + ":5701");
            add(txyIP + ":5702");
            add(txyIP + ":5703");
        }});
        return hzServerDto;
    }

}
