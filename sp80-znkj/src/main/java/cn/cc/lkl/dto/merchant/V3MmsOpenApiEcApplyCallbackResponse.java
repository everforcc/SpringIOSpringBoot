package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=289
 * 合同申请
 * 异步回调参数
 * {"ecApplyId":1018887776751775744,"ecName":"特约商户支付服务合作协议V3.3","ecNo":"QY20250912526101505",
 * "ecStatus":"COMPLETED","orderNo":"687d065c2d444b56aa522f841cf850ae","orgId":1,"version":"1.0"}
 */
@Data
public class V3MmsOpenApiEcApplyCallbackResponse {

    /**
     * 版本号	M	String(32)	1.0
     */
    @JsonProperty("version")
    private String version;

    /**
     * 机构号	M	Integer	合同所属机构号
     */
    @JsonProperty("orgId")
    private Integer orgId;

    /**
     * 请求上送的订单号	M	String(32)
     */
    @JsonProperty("orderNo")
    private String orderNo;

    /**
     * 电子签约申请受理编号	M	Long
     */
    @JsonProperty("ecApplyId")
    private Long ecApplyId;

    /**
     * 电子合同号	M	String(32)	QT20210914000216202
     * 等于合同申请的 V3TkbsMerchantEncryRequest.contractNo
     */
    @JsonProperty("ecNo")
    private String ecNo;

    /**
     * 电子合同名称	M	String(32)	特约商户支付服务合作协议V3.1
     */
    @JsonProperty("ecName")
    private String ecName;

    /**
     * ecStatus	电子合同签署状态	M	String(32)
     * UNDONE 未完成
     * COMPLETED 已完成
     */
    @JsonProperty("ecStatus")
    private String ecStatus;

    public boolean resultCompleted() {
        return "COMPLETED".equals(ecStatus);
    }

}
