package cn.cc.huifu.refund.service;

import cn.cc.huifu.dto.HuiFuInfo;

import java.util.List;

public interface IHuiFuInfoService {

    List<HuiFuInfo> listHuiFuInfo(String reqDate, List<String> hfSeqIdList);

}
