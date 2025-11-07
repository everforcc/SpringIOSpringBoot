package cn.cc.huifu.refund.service.impl;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.mapper.ZnPayOrderRecordMapper;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("iZnPayOrderRecordService138")
public class ZnPayOrderRecordServiceImpl138 implements IZnPayOrderRecordService {
    @Resource
    ZnPayOrderRecordMapper znPayOrderRecordMapper;

    @DS("yq138")
    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord(String reqDate, List<String> hfSeqIdList) {
        log.info("当前数据源: {}", DynamicDataSourceContextHolder.peek());
        return znPayOrderRecordMapper.listZnPayOrderRecord(reqDate, hfSeqIdList);
    }

    @DS("yq138")
    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecordLkl(String reqDate, List<String> hfSeqIdList) {
        return znPayOrderRecordMapper.listZnPayOrderRecordLkl(reqDate, hfSeqIdList);
    }

    @DS("yq138")
    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecordLklSplit(String reqDate, List<String> hfSeqIdList) {
        return znPayOrderRecordMapper.listZnPayOrderRecordLklSplit(reqDate, hfSeqIdList);
    }

}
