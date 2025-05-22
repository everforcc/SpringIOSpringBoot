package cn.cc.huifu.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品系统
 * znkj_huifu_info
 */
@Data
public class HuiFuCommonField {

    /**
     * 请求流水号
     */
    private String reqSeqId;

    /**
     * 请求日期
     */
    private String reqDate;

}
