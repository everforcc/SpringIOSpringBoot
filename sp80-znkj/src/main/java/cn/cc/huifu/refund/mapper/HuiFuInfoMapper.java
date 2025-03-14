package cn.cc.huifu.refund.mapper;

import cn.cc.huifu.dto.HuiFuInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HuiFuInfoMapper {

    List<HuiFuInfo> listHuiFuInfo132(@Param("reqDate") String reqDate, @Param("list") List<String> hfSeqIdList);

    List<HuiFuInfo> listHuiFuInfo152(@Param("reqDate") String reqDate, @Param("list") List<String> hfSeqIdList);
}
