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
@Service
public class ZnPayOrderRecordServiceImpl implements IZnPayOrderRecordService {

    @Resource
    ZnPayOrderRecordMapper znPayOrderRecordMapper;
    @DS("yq136")
    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord136(String reqDate, List<String> hfSeqIdList) {
        log.info("当前数据源: {}", DynamicDataSourceContextHolder.peek());
        return znPayOrderRecordMapper.listZnPayOrderRecord(reqDate, hfSeqIdList);
    }

    @DS("yq138")
    @Override
    public List<ZnPayOrderRecord> listZnPayOrderRecord138(String reqDate, List<String> hfSeqIdList) {
        log.info("当前数据源: {}", DynamicDataSourceContextHolder.peek());
        return znPayOrderRecordMapper.listZnPayOrderRecord(reqDate, hfSeqIdList);
    }
}
