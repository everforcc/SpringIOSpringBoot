package cn.cc.huifu.refund.mapper;

import cn.cc.huifu.dto.HuifuRefund;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuifuRefundMapper {

    HuifuRefund getOneHuifuRefund(@Param("hfSeqId") String hfSeqId, @Param("reqDate") String reqDate);

    int saveHuifuRefund(HuifuRefund huifuRefund);

    List<String> listHfSeqId(@Param("reqDate") String reqDate);
}
