package cn.cc.huifu.refund.service.impl;

import cn.cc.huifu.dto.HuiFuInfo;
import cn.cc.huifu.dto.HuifuRefund;
import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.mapper.HuifuRefundMapper;
import cn.cc.huifu.refund.refund.ZnPayOrderRefund;
import cn.cc.huifu.refund.service.IHuiFuInfoService;
import cn.cc.huifu.refund.service.IRefundService;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

    @Resource
    IHuiFuInfoService iHuiFuInfoService;

    @Resource
    IZnPayOrderRecordService iZnPayOrderRecordService;

    @Resource
    HuifuRefundMapper huifuRefundMapper;


    @Override
    public List<HuifuRefund> refundList() {
        LocalDate localDate = LocalDate.now();
        String reqDate = localDate.getYear() +
                String.format("%02d", localDate.getMonthValue()) +
                String.format("%02d", localDate.getDayOfMonth());

        List<HuifuRefund> huifuRefundList = new ArrayList<>();

        List<String> hfSeqIdList = huifuRefundMapper.listHfSeqId(reqDate);

        log.info("已处理退款数据: {}", hfSeqIdList.size());

        List<ZnPayOrderRecord> znPayOrderRecordList = iZnPayOrderRecordService.listZnPayOrderRecord(reqDate, hfSeqIdList);
        log.info("园区待处理数据: {}", znPayOrderRecordList.size());
        // 园区
        for (ZnPayOrderRecord znPayOrderRecord : znPayOrderRecordList) {
            String otherDataHfSeqId = znPayOrderRecord.getOtherDataHfSeqId();
            Long payAmt = znPayOrderRecord.getPayAmt();
//            double amt = (double) payAmt / 100;
            String amtStr = String.valueOf(payAmt);
            if(amtStr.length()>2){
                amtStr = amtStr.substring(0,amtStr.length()-2) + "." + amtStr.substring(amtStr.length()-2, amtStr.length());
            }else {
                amtStr = "0." + amtStr;
            }

            log.info("园区 退款 日期: {}, 汇付id: {}, 金额: {}, {}", reqDate, otherDataHfSeqId, payAmt, amtStr);
            otherDataHfSeqId = otherDataHfSeqId.replace("\"","");
//            HuifuRefund exist = huifuRefundMapper.getOneHuifuRefund(otherDataHfSeqId, reqDate);
//            if (Objects.nonNull(exist)) {
//                log.info("退款数据已存在: {}", exist.toString());
//                huifuRefundList.add(exist);
//                continue;
//            }

            Map<String, Object> resultMap = ZnPayOrderRefund.refundFlow(otherDataHfSeqId, reqDate, amtStr);
            if (Objects.nonNull(resultMap)) {
                String bank_code = (String) resultMap.get("bank_code");
                String resp_desc = (String) resultMap.get("resp_desc");
                HuifuRefund huifuRefund = new HuifuRefund();
                huifuRefund.setHfSeqId(otherDataHfSeqId);
                huifuRefund.setBankCode(bank_code);
                huifuRefund.setRespDesc(resp_desc);
                huifuRefund.setReqDate(reqDate);
                huifuRefund.setAmt(amtStr);
                huifuRefund.setResponse(resultMap.toString());
                huifuRefund.setType("园区");
                huifuRefund.setSysTime(znPayOrderRecord.getCreateTime());
                huifuRefundMapper.saveHuifuRefund(huifuRefund);
                huifuRefundList.add(huifuRefund);
            }
        }
        // 订单
        List<HuiFuInfo> huiFuInfoList = iHuiFuInfoService.listHuiFuInfo(reqDate, hfSeqIdList);
        log.info("订单待处理数据: {}", huiFuInfoList.size());
        for (HuiFuInfo huiFuInfo : huiFuInfoList) {
            String otherDataHfSeqId = huiFuInfo.getHfSeqId();
            String payAmt = huiFuInfo.getTransAmt();
            log.info("订单 退款 日期: {}, 汇付id: {}, 金额: {}", reqDate, otherDataHfSeqId, payAmt);

//            HuifuRefund exist = huifuRefundMapper.getOneHuifuRefund(otherDataHfSeqId, reqDate);
//            if (Objects.nonNull(exist)) {
//                log.info("退款数据已存在: {}", exist.toString());
//                huifuRefundList.add(exist);
//                continue;
//            }

            Map<String, Object> resultMap = ZnPayOrderRefund.refundFlow(otherDataHfSeqId, reqDate, payAmt);
            if (Objects.nonNull(resultMap)) {
                String bank_code = (String) resultMap.get("bank_code");
                String resp_desc = (String) resultMap.get("resp_desc");
                HuifuRefund huifuRefund = new HuifuRefund();
                huifuRefund.setHfSeqId(otherDataHfSeqId);
                huifuRefund.setBankCode(bank_code);
                huifuRefund.setRespDesc(resp_desc);
                huifuRefund.setReqDate(reqDate);
                huifuRefund.setAmt(String.valueOf(payAmt));
                huifuRefund.setResponse(resultMap.toString());
                huifuRefund.setType("订单");
                huifuRefund.setSysTime(huiFuInfo.getCreateTime());
                huifuRefundMapper.saveHuifuRefund(huifuRefund);
                huifuRefundList.add(huifuRefund);
            }
        }

        return huifuRefundList;
    }
}
