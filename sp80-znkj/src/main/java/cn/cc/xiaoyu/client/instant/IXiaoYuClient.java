package cn.cc.xiaoyu.client.instant;

public interface IXiaoYuClient {

    byte[] getLoginData();

    byte[] getHeartData();

    byte[] getPortStatusData(byte[] orderArr, byte port);

    byte[] getEndElecData(byte[] orderArr, byte port, byte endType);

    byte[] getId();

}
