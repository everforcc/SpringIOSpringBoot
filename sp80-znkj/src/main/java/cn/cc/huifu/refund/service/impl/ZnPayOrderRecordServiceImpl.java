package cn.cc.huifu.refund.service.impl;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.mapper.ZnPayOrderRecordMapper;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZnPayOrderRecordServiceImpl implements IZnPayOrderRecordService {

    @Resource
    ZnPayOrderRecordMapper znPayOrderRecordMapper;

    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord(String reqDate, List<String> hfSeqIdList) {
        return znPayOrderRecordMapper.listZnPayOrderRecord(reqDate, hfSeqIdList);
    }
}
