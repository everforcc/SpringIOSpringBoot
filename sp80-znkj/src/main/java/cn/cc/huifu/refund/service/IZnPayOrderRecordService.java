package cn.cc.huifu.refund.service;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

public interface IZnPayOrderRecordService {

    @DS("yq136")
    List<ZnPayOrderRecord> listZnPayOrderRecord136(String reqDate, List<String> hfSeqIdList);

    @DS("yq138")
    List<ZnPayOrderRecord> listZnPayOrderRecord138(String reqDate, List<String> hfSeqIdList);

}
