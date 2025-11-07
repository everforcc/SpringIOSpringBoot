package cn.cc.huifu.refund.service;

import cn.cc.huifu.dto.ZnPayOrderRecord;

import java.util.List;

/**
 * 获取待退款数据
 */
public interface IZnPayOrderRecordService {

    List<ZnPayOrderRecord> listZnPayOrderRecord(String reqDate, List<String> hfSeqIdList);

    List<ZnPayOrderRecord> listZnPayOrderRecordLkl(String reqDate, List<String> hfSeqIdList);

    List<ZnPayOrderRecord> listZnPayOrderRecordLklSplit(String reqDate, List<String> hfSeqIdList);

}
