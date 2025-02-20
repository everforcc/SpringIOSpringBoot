package cn.cc.huifu.refund.service.impl;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.mapper.ZnPayOrderRecordMapper;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZnPayOrderRecordServiceImpl implements IZnPayOrderRecordService {

    @Resource
    ZnPayOrderRecordMapper znPayOrderRecordMapper;

    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord136(String reqDate, List<String> hfSeqIdList) {
        return znPayOrderRecordMapper.listZnPayOrderRecord136(reqDate, hfSeqIdList);
    }


    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord138(String reqDate, List<String> hfSeqIdList) {
        return znPayOrderRecordMapper.listZnPayOrderRecord138(reqDate, hfSeqIdList);
    }
}
