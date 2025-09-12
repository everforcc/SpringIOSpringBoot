package cn.cc.lkl.dto.merchantupdate;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * https://o.lakala.com/p/#/document/detail?id=1153
 * 增终进件
 * /api/v3/tkbs/open_merchant_addTerm
 */
@Data
public class V3TkbsOpenMerchantAddTermRequest extends LKLBaseRequest {
    /**
     * 外部商户编号
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * 业务类型(见附录-业务类型表)
     */
    @JsonProperty("bz_pos")
    private String bzPos;

    /**
     * 终端数量
     */
    @JsonProperty("term_num")
    private Integer termNum;

    /**
     * 网点编号
     */
    @JsonProperty("shop_id")
    private Long shopId;

    /**
     * 费率信息集合
     */
    @JsonProperty("fees")
    private List<FeeInfoDto> fees;

    /**
     * 附件信息集合
     */
    @JsonProperty("attachments")
    private List<AttachmentDto> attachments;

    /**
     * 鉴权机构号
     */
    @JsonProperty("org_code")
    private String orgCode;

    /**
     *
     * @return
     */

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_OPEN_MERCHANT_ADDTERM;
    }
}
