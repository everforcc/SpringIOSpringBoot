package cn.cc.huifu.refund.mapper;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZnPayOrderRecordMapper {

    List<ZnPayOrderRecord> listZnPayOrderRecord(@Param("reqDate") String reqDate, @Param("list") List<String> hfSeqIdList);

    List<ZnPayOrderRecord> listZnPayOrderRecordLkl(@Param("reqDate") String reqDate, @Param("list") List<String> hfSeqIdList);

    List<ZnPayOrderRecord> listZnPayOrderRecordLklSplit(@Param("reqDate") String reqDate, @Param("list") List<String> hfSeqIdList);

}
