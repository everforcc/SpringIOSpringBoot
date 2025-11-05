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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

    @Resource
    IHuiFuInfoService iHuiFuInfoService;

    @Resource
    IZnPayOrderRecordService iZnPayOrderRecordService;

    @Resource
    HuifuRefundMapper huifuRefundMapper;

    List<String> yqIPList = Arrays.asList("192.168.1.136", "192.168.1.138");

    List<String> boxIPList = Arrays.asList("192.168.1.132");

    @Override
    public List<HuifuRefund> refundList() {
        LocalDate localDate = LocalDate.now();
        String reqDate = localDate.getYear() +
                String.format("%02d", localDate.getMonthValue()) +
                String.format("%02d", localDate.getDayOfMonth());

        // 退款清单
        List<HuifuRefund> huifuRefundList = new ArrayList<>();

        for (String ip : yqIPList) {
            List<String> hfSeqIdList = huifuRefundMapper.listHfSeqId(reqDate, ip);

            log.info("{}: 园区 已处理退款数据: {}", ip, hfSeqIdList.size());

            List<ZnPayOrderRecord> znPayOrderRecordList;
            if ("192.168.1.136".equals(ip)) {
                znPayOrderRecordList = iZnPayOrderRecordService.listZnPayOrderRecord136(reqDate, hfSeqIdList);
            } else {
                znPayOrderRecordList = iZnPayOrderRecordService.listZnPayOrderRecord138(reqDate, hfSeqIdList);
            }

            log.info("{}: 园区 待处理数据: {}", ip, znPayOrderRecordList.size());
            // 园区
            for (ZnPayOrderRecord znPayOrderRecord : znPayOrderRecordList) {
                String otherDataHfSeqId = znPayOrderRecord.getOtherDataHfSeqId();
                if(StringUtils.isEmpty(otherDataHfSeqId)){
                    continue;
                }
                Long payAmt = znPayOrderRecord.getPayAmt();
//            double amt = (double) payAmt / 100;
                String amtStr = String.valueOf(payAmt);
                if (amtStr.length() > 2) {
                    amtStr = amtStr.substring(0, amtStr.length() - 2) + "." + amtStr.substring(amtStr.length() - 2, amtStr.length());
                } else {
                    if (amtStr.length() == 2) {
                        amtStr = "0." + amtStr;
                    } else if (amtStr.length() == 1) {
                        amtStr = "0.0" + amtStr;
                    }
                }

                log.info("{}:园区 退款 日期: {}, 汇付id: {}, 金额: {}, {}", ip, reqDate, otherDataHfSeqId, payAmt, amtStr);
                otherDataHfSeqId = otherDataHfSeqId.replace("\"", "");
//            HuifuRefund exist = huifuRefundMapper.getOneHuifuRefund(otherDataHfSeqId, reqDate);
//            if (Objects.nonNull(exist)) {
//                log.info("退款数据已存在: {}", exist.toString());
//                huifuRefundList.add(exist);
//                continue;
//            }

                Map<String, Object> resultMap = ZnPayOrderRefund.refundFlow(otherDataHfSeqId, reqDate, amtStr, znPayOrderRecord.getHuifuid());
                if (Objects.nonNull(resultMap)) {
                    String bank_code = (String) resultMap.get("bank_code");
                    String resp_desc = (String) resultMap.get("resp_desc");
                    HuifuRefund huifuRefund = new HuifuRefund();
                    huifuRefund.setIp(ip);
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
        }

        for (String ip : boxIPList) {
            List<String> hfSeqIdList = huifuRefundMapper.listHfSeqId(reqDate, ip);

            log.info("{}: 订单 已处理退款数据: {}", ip, hfSeqIdList.size());
            List<HuiFuInfo> huiFuInfoList;
            // 订单 192.168.1.132
            if ("192.168.1.132".equals(ip)) {
                huiFuInfoList = iHuiFuInfoService.listHuiFuInfo132(reqDate, hfSeqIdList);
            } else {
                huiFuInfoList = iHuiFuInfoService.listHuiFuInfo152(reqDate, hfSeqIdList);
            }
            log.info("{}: 订单 待处理数据: {}", ip, huiFuInfoList.size());
            for (HuiFuInfo huiFuInfo : huiFuInfoList) {
                String otherDataHfSeqId = huiFuInfo.getHfSeqId();
                String payAmt = huiFuInfo.getTransAmt();
                log.info("{}: 订单 退款 日期: {}, 汇付id: {}, 金额: {}", ip, reqDate, otherDataHfSeqId, payAmt);

//            HuifuRefund exist = huifuRefundMapper.getOneHuifuRefund(otherDataHfSeqId, reqDate);
//            if (Objects.nonNull(exist)) {
//                log.info("退款数据已存在: {}", exist.toString());
//                huifuRefundList.add(exist);
//                continue;
//            }

                Map<String, Object> resultMap = ZnPayOrderRefund.refundFlow(otherDataHfSeqId, reqDate, payAmt, huiFuInfo.getHuifuId());
                if (Objects.nonNull(resultMap)) {
                    String bank_code = (String) resultMap.get("bank_code");
                    String resp_desc = (String) resultMap.get("resp_desc");
                    HuifuRefund huifuRefund = new HuifuRefund();
                    huifuRefund.setIp(ip);
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
        }

        return huifuRefundList;
    }
}
