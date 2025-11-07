package cn.cc.huifu.refund.service.impl;

import cn.cc.config.JsonUtil;
import cn.cc.huifu.dto.HuiFuInfo;
import cn.cc.huifu.dto.HuifuRefund;
import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.mapper.HuifuRefundMapper;
import cn.cc.huifu.refund.refund.ZnPayOrderRefund;
import cn.cc.huifu.refund.service.IHuiFuInfoService;
import cn.cc.huifu.refund.service.IRefundService;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.ordersplit.V3SacsSeparateResponse;
import cn.cc.lkl.service.LKLRefundService;
import cn.cc.util.DateUtils;
import com.lkl.laop.sdk.request.model.V3SacsSeparateRecvDatas;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

    @Resource
    IHuiFuInfoService iHuiFuInfoService;

    @Resource
    List<IZnPayOrderRecordService> iZnPayOrderRecordServiceList;

    @Resource
    HuifuRefundMapper huifuRefundMapper;

//    List<String> yqIPList = Arrays.asList("192.168.1.136", "192.168.1.138");

    List<String> boxIPList = Arrays.asList("192.168.1.132");
    // 初始化一个map，随便给个示例参数
    public static Map<String, String> ipMap = new HashMap<>();

    static {
        ipMap.put("ZnPayOrderRecordServiceImpl136", "192.168.1.136");
        ipMap.put("ZnPayOrderRecordServiceImpl138", "192.168.1.138");
    }

    @Resource
    LKLRefundService lklRefundService;


    /**
     * 调整
     * 分为以下几种情况
     * 1. 删除 boxIPList 没有和子退款了
     * 2. 云端ip，136,138,148
     * 3. 退款汇付
     * 4. 退款拉卡拉 非分账情况
     * 5. 退款拉卡拉 分账情况
     */
    @Override
    public List<HuifuRefund> refundListYqHf() {
        String reqDate = DateUtils.getDate();
        // 退款清单
        List<HuifuRefund> huifuRefundList = new ArrayList<>();
        for (IZnPayOrderRecordService znPayOrderRecordService : iZnPayOrderRecordServiceList) {
            // 获取znPayOrderRecordService的servicename
            String yqKey = znPayOrderRecordService.getClass().getSimpleName();
            yqKey = yqKey.substring(0, yqKey.indexOf("$"));
            String ip = ipMap.get(yqKey);
            List<String> hfSeqIdList = huifuRefundMapper.listHfSeqId(reqDate, ip);
            log.info("{}: 园区 已处理退款数据: {}", ip, hfSeqIdList.size());
            List<ZnPayOrderRecord> znPayOrderRecordList = znPayOrderRecordService.listZnPayOrderRecord(reqDate, hfSeqIdList);
            log.info("{}: 园区 待处理数据: {}", ip, znPayOrderRecordList.size());
            // 园区
            for (ZnPayOrderRecord znPayOrderRecord : znPayOrderRecordList) {
                String otherDataHfSeqId = znPayOrderRecord.getOtherDataHfSeqId();
                if (StringUtils.isEmpty(otherDataHfSeqId)) {
                    continue;
                }
                Long payAmt = znPayOrderRecord.getPayAmt();

                String amtStr = formatCurrency(payAmt);
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
                    huifuRefund.setType("园区-汇付");
                    huifuRefund.setSysTime(znPayOrderRecord.getCreateTime());
                    huifuRefundMapper.saveHuifuRefund(huifuRefund);
                    huifuRefundList.add(huifuRefund);
                }
            }
        }
        return huifuRefundList;
    }

    @Override
    public List<HuifuRefund> refundListBoxHf() {
        String reqDate = DateUtils.getDate();

        // 退款清单
        List<HuifuRefund> huifuRefundList = new ArrayList<>();
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

    @Override
    public List<HuifuRefund> refundListYqLkl() {
        String reqDate = DateUtils.getDate();
        // 退款清单
        List<HuifuRefund> huifuRefundList = new ArrayList<>();
        for (IZnPayOrderRecordService znPayOrderRecordService : iZnPayOrderRecordServiceList) {
            // 获取znPayOrderRecordService的servicename
            String yqKey = znPayOrderRecordService.getClass().getSimpleName();
            // ZnPayOrderRecordServiceImpl136$$EnhancerBySpringCGLIB$$852f8bce
            // 获取实现类的类名
            yqKey = yqKey.substring(0, yqKey.indexOf("$"));
            log.info("拉卡拉非分账:{}: 获取数据源: {}", yqKey, znPayOrderRecordService.getClass().getSimpleName());
            String ip = ipMap.get(yqKey);
            List<String> existIdList = huifuRefundMapper.listHfSeqId(reqDate, ip);

            log.info("拉卡拉非分账:{}: 园区 已处理退款数据: {}", ip, existIdList.size());

            List<ZnPayOrderRecord> znPayOrderRecordList = znPayOrderRecordService.listZnPayOrderRecordLkl(reqDate, existIdList);

            log.info("拉卡拉非分账:{}: 园区 待处理数据: {}", ip, znPayOrderRecordList.size());
            // 园区
            for (ZnPayOrderRecord znPayOrderRecord : znPayOrderRecordList) {
                String otherDataHfSeqId = znPayOrderRecord.getOtherDataHfSeqId();
                if (StringUtils.isEmpty(otherDataHfSeqId)) {
                    continue;
                }
                Long payAmt = znPayOrderRecord.getPayAmt();

                String amtStr = formatCurrency(payAmt);
                log.info("拉卡拉非分账:{}:园区 退款 日期: {}, 拉卡拉id: {}, 金额: {}, {}", ip, reqDate, otherDataHfSeqId, payAmt, amtStr);
                LKLCommonResponse response = lklRefundService.refund(znPayOrderRecord.getLklMerchantNo(), znPayOrderRecord.getLklTermNo(), znPayOrderRecord.getReqSeqid(), String.valueOf(znPayOrderRecord.getPayAmt()));
                if (Objects.nonNull(response)) {
                    HuifuRefund huifuRefund = new HuifuRefund();
                    huifuRefund.setIp(ip);
                    huifuRefund.setHfSeqId(otherDataHfSeqId);
                    huifuRefund.setBankCode(response.getCode());
                    huifuRefund.setRespDesc(response.getMsg());
                    huifuRefund.setReqDate(reqDate);
                    huifuRefund.setAmt(amtStr);
                    huifuRefund.setResponse(JsonUtil.toJson(response));
                    huifuRefund.setType("园区-拉卡拉");
                    huifuRefund.setSysTime(znPayOrderRecord.getCreateTime());
                    huifuRefundMapper.saveHuifuRefund(huifuRefund);
                    huifuRefundList.add(huifuRefund);
                }
            }
        }
        return huifuRefundList;
    }

    @Override
    public List<HuifuRefund> refundListYqLklSplit() {
        String reqDate = DateUtils.getDate();

        // 退款清单
        List<HuifuRefund> huifuRefundList = new ArrayList<>();

        for (IZnPayOrderRecordService znPayOrderRecordService : iZnPayOrderRecordServiceList) {
            // 获取znPayOrderRecordService的servicename
            String yqKey = znPayOrderRecordService.getClass().getSimpleName();
            // ZnPayOrderRecordServiceImpl136$$EnhancerBySpringCGLIB$$852f8bce
            // 获取实现类的类名
            yqKey = yqKey.substring(0, yqKey.indexOf("$"));
            log.info("拉卡拉分账:{}: 获取数据源: {}", yqKey, znPayOrderRecordService.getClass().getSimpleName());
            String ip = ipMap.get(yqKey);
            List<String> existIdList = huifuRefundMapper.listHfSeqId(reqDate, ip);

            log.info("拉卡拉分账:{}: 园区 已处理退款数据: {}", ip, existIdList.size());

            List<ZnPayOrderRecord> znPayOrderRecordList = znPayOrderRecordService.listZnPayOrderRecordLklSplit(reqDate, existIdList);

            log.info("拉卡拉分账:{}: 园区 待处理数据: {}", ip, znPayOrderRecordList.size());
            // 园区
            for (ZnPayOrderRecord znPayOrderRecord : znPayOrderRecordList) {
                String otherDataHfSeqId = znPayOrderRecord.getOtherDataHfSeqId();
                if (StringUtils.isEmpty(otherDataHfSeqId)) {
                    continue;
                }
                Long payAmt = znPayOrderRecord.getPayAmt();

                String amtStr = formatCurrency(payAmt);
                log.info("拉卡拉分账:{}:园区 退款 日期: {}, 拉卡拉分账id: {}, 金额: {}, {}", ip, reqDate, otherDataHfSeqId, payAmt, amtStr);
                String lklSplitRes = znPayOrderRecord.getLklSplitRes();
                LKLCommonResponse lklCommonResponse = JsonUtil.fromJson(lklSplitRes, LKLCommonResponse.class);
                V3SacsSeparateResponse v3SacsSeparateResponse = JsonUtil.fromJson(lklCommonResponse.getRespData(), V3SacsSeparateResponse.class);

                log.info("拉卡拉分账:{}: 待退款回调信息: {}", ip, v3SacsSeparateResponse);
                String splitInfo = znPayOrderRecord.getAcctSplitInfo();
                List<V3SacsSeparateRecvDatas> recvDatas = JsonUtil.parseToList(splitInfo, V3SacsSeparateRecvDatas.class);
                log.info("拉卡拉分账:{}: 待退款分账信息-: {}", ip, recvDatas);

                for (V3SacsSeparateRecvDatas recvData : recvDatas) {

                    if (StringUtils.isNotEmpty(recvData.getRecvNo())) {
                        log.info("拉卡拉分账:{}: 退款信息-分账-商户号: {}", ip, recvData.getRecvNo());
                        // 回退
                        lklRefundService.refundZnkjBack(v3SacsSeparateResponse.getSeparateNo(), recvData.getSeparateValue(), recvData.getRecvNo());
                        // 退款
                        LKLCommonResponse response = lklRefundService.refund(znPayOrderRecord.getLklMerchantNo(), znPayOrderRecord.getLklTermNo(), znPayOrderRecord.getReqSeqid(), String.valueOf(znPayOrderRecord.getPayAmt()));
                        if (Objects.nonNull(response)) {
                            HuifuRefund huifuRefund = new HuifuRefund();
                            huifuRefund.setIp(ip);
                            huifuRefund.setHfSeqId(otherDataHfSeqId);
                            huifuRefund.setBankCode(response.getCode());
                            huifuRefund.setRespDesc(response.getMsg());
                            huifuRefund.setReqDate(reqDate);
                            huifuRefund.setAmt(amtStr);
                            huifuRefund.setResponse(JsonUtil.toJson(response));
                            huifuRefund.setType("园区-拉卡拉-分账");
                            huifuRefund.setSysTime(znPayOrderRecord.getCreateTime());
                            huifuRefundMapper.saveHuifuRefund(huifuRefund);
                            huifuRefundList.add(huifuRefund);
                        }
                    }
                }
            }
        }
        return huifuRefundList;
    }

    private String formatCurrency(Long amount) {
        if (amount == null) {
            return "0.00";
        }
        return String.format("%.2f", amount / 100.0);
    }


}
