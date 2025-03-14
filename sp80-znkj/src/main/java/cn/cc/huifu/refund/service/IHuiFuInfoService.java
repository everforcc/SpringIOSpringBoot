package cn.cc.huifu.refund.service;

import cn.cc.huifu.dto.HuiFuInfo;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

public interface IHuiFuInfoService {

    @DS("dd132")
    List<HuiFuInfo> listHuiFuInfo132(String reqDate, List<String> hfSeqIdList);

    @DS("dd152")
    List<HuiFuInfo> listHuiFuInfo152(String reqDate, List<String> hfSeqIdList);

}
