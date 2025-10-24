package cn.cc.lkl.dto.merchant;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * https://o.lakala.com/#/home/document/detail?id=289
 * 电子合同申请
 * /api/v3/mms/open_api/ec/apply
 */
@Data
public class V3MmsOpenApiEcApplyRequest extends LKLBaseRequest {

    /**
     * 四方机构自定义订单编号
     * 建议：平台编号+14位年月日时（24小时制）分秒+8位的随机数（同一接入机构不重复）
     */
    @NotBlank
    @Size(max = 32, message = "订单编号长度不能超过32位")
    @JsonProperty("order_no")
    private String orderNo;

    /**
     * 机构号
     * 签约方所属拉卡拉机构
     */
    @NotNull
    @JsonProperty("org_id")
    private Integer orgId;

    /**
     * 合同类别
     * EC001 : 特约商户支付服务合作协议V3.1(商户入网)
     * EC002 : 特约商户支付服务合作协议V3.2（商户入网+分账）
     * EC003 : 分账结算授权委托书
     * EC004 : 特约商户支付服务合作协议V3.3（商户入网）
     * EC005 : 特约商户支付服务合作协议V3.3（商户入网+分账）
     * EC007 : 特约商户支付服务合作协议V4.1 + 结算授权委托书 (商户入网 + 分账 )
     * EC008 : 特约商户支付服务合作协议V4.1 (商户入网)
     * EC009 : 结算授权委托书
     */
    @NotBlank
    @Size(max = 12, message = "合同类别长度不能超过12位")
    @JsonProperty("ec_type_code")
    private String ecTypeCode = "EC007";

    /**
     * 法人/经营者证件类型
     * RESIDENT_ID（身份证）； PASSPORT（护照）； HK_MACAO_PASS（港澳居民往来内地通行证）； TAIWAN_PASS（台湾居民来往大陆通行证）；
     */
    @NotBlank
    @Size(max = 16, message = "证件类型长度不能超过16位")
    @JsonProperty("cert_type")
    private String certType;

    /**
     * 法人/经营者姓名
     */
    @NotBlank
    @Size(max = 32, message = "姓名长度不能超过32位")
    @JsonProperty("cert_name")
    private String certName;

    /**
     * 法人/经营者证件号码
     */
    @NotBlank
    @Size(max = 32, message = "证件号码长度不能超过32位")
    @JsonProperty("cert_no")
    private String certNo;

    /**
     * 签约手机号
     * 1.小微个人商户（无营业执照），签约手机号必须填写商户经营者本人手机号；
     * 2.个体工商户或企业商户（有营业执照），签约手机号必须填写法人手机号或者经办人手机号
     * 合同签署人手机号，请慎重填写，不可修改
     */
    @NotBlank
    @Size(max = 16, message = "手机号长度不能超过16位")
    @JsonProperty("mobile")
    private String mobile;

    /**
     * 营业执照号
     * 个体工商户或企业商户 必传
     */
    @Size(max = 32, message = "营业执照号长度不能超过32位")
    @JsonProperty("business_license_no")
    private String businessLicenseNo;

    /**
     * 营业执照名称
     * 个体工商户或企业商户 必传
     */
    @Size(max = 32, message = "营业执照名称长度不能超过32位")
    @JsonProperty("business_license_name")
    private String businessLicenseName;

    /**
     * 企业/经营者结算开户行号
     */
    @NotBlank
    @Size(max = 32, message = "开户行号长度不能超过32位")
    @JsonProperty("openning_bank_code")
    private String openningBankCode;

    /**
     * 企业/经营者结算开户行名称
     */
    @NotBlank
    @Size(max = 128, message = "开户行名称长度不能超过128位")
    @JsonProperty("openning_bank_name")
    private String openningBankName;

    /**
     * 企业/经营者结算卡性质
     * 57 对公、 58 对私
     */
    @NotBlank
    @Size(max = 2, message = "结算卡性质长度不能超过2位")
    @JsonProperty("acct_type_code")
    private String acctTypeCode;

    /**
     * 企业/经营者结算卡号
     */
    @NotBlank
    @Size(max = 32, message = "结算卡号长度不能超过32位")
    @JsonProperty("acct_no")
    private String acctNo;

    /**
     * ~~企业/经营者结算卡名称~~
     * 法人 姓名
     */
    @NotBlank
    @Size(max = 64, message = "结算卡名称长度不能超过64位")
    @JsonProperty("acct_name")
    private String acctName;

    /**
     * 电子合同内容参数集合
     * 按合同类型（ecTypeCode）传递不同的参数集合
     */
    @NotBlank
    @JsonProperty("ec_content_parameters")
    private String ecContentParameters;

    /**
     * 是否经办签约
     * 0 不启用 1启用 ； 缺省 0
     */
    @JsonProperty("agent_tag")
    private Integer agentTag;

    /**
     * 经办人名称
     * 经办人名称（要与证件号对应）
     * agentTag 为1时 必传
     */
    @Size(max = 32, message = "经办人名称长度不能超过32位")
    @JsonProperty("agent_name")
    private String agentName;

    /**
     * 经办人证件类型
     * RESIDENT_ID（身份证）； PASSPORT（护照）； HK_MACAO_PASS（港澳居民往来内地通行证）； TAIWAN_PASS（台湾居民来往大陆通行证）；
     * agentTag 为1时 必传
     */
    @Size(max = 32, message = "经办人证件类型长度不能超过32位")
    @JsonProperty("agent_cert_type")
    private String agentCertType;

    /**
     * 经办人证件号
     * agentTag 为1时 必传
     */
    @Size(max = 32, message = "经办人证件号长度不能超过32位")
    @JsonProperty("agent_cert_no")
    private String agentCertNo;

    /**
     * 经办签约授权委托书文件名
     * agentTag 为1时 必传
     */
    @Size(max = 32, message = "授权委托书文件名长度不能超过32位")
    @JsonProperty("agent_file_name")
    private String agentFileName;

    /**
     * 经办授权委托书文件路径
     * agentTag 为1时 必传
     */
    @Size(max = 128, message = "授权委托书文件路径长度不能超过128位")
    @JsonProperty("agent_file_path")
    private String agentFilePath;

    /**
     * 备注说明
     */
    @Size(max = 128, message = "备注说明长度不能超过128位")
    @JsonProperty("remark")
    private String remark;

    /**
     * 电子合同签约结果回调通知
     * 成功签约才通知
     */
    @Size(max = 128, message = "回调通知地址长度不能超过128位")
    @JsonProperty("ret_url")
    private String retUrl;

    @Override
    public FunctionCodeEnum gFunctionCode() {
        return FunctionCodeEnum.API_V3_MMS_OPEN_API_EC_APPLY;
    }
}