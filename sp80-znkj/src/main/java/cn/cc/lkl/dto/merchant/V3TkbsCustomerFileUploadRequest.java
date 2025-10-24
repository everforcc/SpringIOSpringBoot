package cn.cc.lkl.dto.merchant;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商户进件文件上传
 * https://o.lakala.com/p/#/document/detail?id=1143
 */
@Data
public class V3TkbsCustomerFileUploadRequest extends LKLBaseRequest {

//    reqData.put("file_base64", "data:image/png;base64," + fileBase64);
//        reqData.put("img_type", "SHOP_OUTSIDE_IMG");
//        reqData.put("is_ocr", "false");
//        reqData.put("sourcechnl", "0");
//        reqData.put("prefix", "reg");

    @NotBlank
    @JsonProperty("file_base64")
    private String fileBase64;

    /**
     *
     * 图片类型 https://o.lakala.com/p/#/document/detail?id=1173
     * ID_CARD_FRONT	身份证正⾯（必传）
     * ID_CARD_BEHIND	身份证反⾯（必传）
     * BUSINESS_LICENCE	营业执照照⽚（企业必传）
     * BANK_CARD	银行卡（企业对公不需要传）
     * AGREE_MENT	入网协议
     * OPENING_PERMIT	开户许可证（企业对公需要传）
     * CHECKSTAND_IMG	收银台照片(必传)
     * SHOP_OUTSIDE_IMG	上传门头照片(必传)
     * SHOP_INSIDE_IMG	商铺内部照片(必传)
     * OTHERS	无对应类型图片，请传其他类型
     * SETTLE_ID_CARD_FRONT	结算人身份证人像面
     * SETTLE_ID_CARD_BEHIND	结算人身份证国徽面
     * LETTER_OF_AUTHORIZATION	法人授权涵
     */
    @NotBlank
    @JsonProperty("img_type")
    private String imgType;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 0:PC,1:安卓,2:IOS
     */
    @NotBlank
    @JsonProperty("sourcechnl")
    private String sourcechnl = "0";

    /**
     * 是否需要ocr	默认传 false
     */
    @NotBlank
    @JsonProperty("is_ocr")
    private String isOcr = "false";

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = "data:image/png;base64," + fileBase64;
    }

    @Override
    public FunctionCodeEnum gFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_CUSTOMER_FILE_UPLOAD;
    }
}
