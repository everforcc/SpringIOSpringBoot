package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 合同相应
 */
@Data
public class V3MmsOpenApiEcApplyResponse {

    /**
     * order_no	请求上送的订单号	M	String(32)
     *  ec_apply_id	电子签约申请受理编号	M	Long
     *       result_url	电子签约申请结果H5链接地址(长度较长2000+)	M	String	申请成功时：待签约合同H5链接
     * 申请失败时：错误信息结果H5链接
     */

    /**
     * 请求上送的订单号	M	String(32)
     */
    @JsonProperty("order_no")
    private String orderNo;

    /**
     * 电子签约申请受理编号	M	Long
     */
    @JsonProperty("ec_apply_id")
    private Long ecApplyId;

    /**
     * 电子签约申请结果H5链接地址(长度较长2000+)	M	String	申请成功时：待签约合同H5链接
     * 申请失败时：错误信息结果H5链接
     */
    @JsonProperty("result_url")
    private String resultUrl;

}
