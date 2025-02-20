package cn.cc.huifu.refund.service;

import cn.cc.huifu.dto.ZnPayOrderRecord;

import java.util.List;

public interface IZnPayOrderRecordService {

    List<ZnPayOrderRecord> listZnPayOrderRecord136(String reqDate, List<String> hfSeqIdList);

    List<ZnPayOrderRecord> listZnPayOrderRecord138(String reqDate, List<String> hfSeqIdList);

}
