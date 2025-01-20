package cn.cc.huifu.refund.service.impl;

import cn.cc.huifu.dto.HuiFuInfo;
import cn.cc.huifu.refund.mapper.HuiFuInfoMapper;
import cn.cc.huifu.refund.service.IHuiFuInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HuiFuInfoServiceImpl implements IHuiFuInfoService {

    @Resource
    HuiFuInfoMapper huiFuInfoMapper;

    @Override
    public List<HuiFuInfo> listHuiFuInfo(String reqDate, List<String> hfSeqIdList) {
        return huiFuInfoMapper.listHuiFuInfo(reqDate, hfSeqIdList);
    }

}
