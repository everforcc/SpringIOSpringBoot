package cn.cc.huifu.refund.controller;

import cn.cc.core.domain.R;
import cn.cc.huifu.dto.HuiFuInfo;
import cn.cc.huifu.dto.HuifuRefund;
import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.service.IHuiFuInfoService;
import cn.cc.huifu.refund.service.IRefundService;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/refund")
public class RefundController {

    @Resource
    IZnPayOrderRecordService iZnPayOrderRecordService;

    @Resource
    IHuiFuInfoService iHuiFuInfoService;

    @Resource
    IRefundService iRefundService;

    @GetMapping("/yq/{reqDate}")
    public R<List<ZnPayOrderRecord>> refundYq(@PathVariable("reqDate") String reqDate) {
        return R.ok(iZnPayOrderRecordService.listZnPayOrderRecord(reqDate, null));
    }

    @GetMapping("/yqlocal{reqDate}")
    public R<List<HuiFuInfo>> refundYqLocal(@PathVariable("reqDate") String reqDate) {
        return R.ok(iHuiFuInfoService.listHuiFuInfo(reqDate, null));
    }

    @GetMapping()
    public R<List<HuifuRefund>> refund() {
        return R.ok(iRefundService.refundList());
    }

}
