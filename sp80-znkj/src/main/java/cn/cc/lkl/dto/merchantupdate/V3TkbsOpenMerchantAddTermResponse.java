package cn.cc.lkl.dto.merchantupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 增终进件
 * 返回参数
 */
@Data
public class V3TkbsOpenMerchantAddTermResponse {

    /**
     * number(double)
     */
    @JsonProperty("message")
    private String message;

    /**
     * 变更凭证id[变更结果可通过id进行查询]
     * string
     */
    @JsonProperty("review_related_id")
    private String reviewRelatedId;

}
