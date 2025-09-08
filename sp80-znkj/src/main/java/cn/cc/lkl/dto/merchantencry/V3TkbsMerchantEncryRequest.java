package cn.cc.lkl.dto.merchantencry;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 新增商户进件-请求体 V3TkbsMerchantEncryRequest
 * 依据 docs/lkl/接口文档/新增商户进件.md 定义生成
 */
@Data
public class V3TkbsMerchantEncryRequest extends LKLBaseRequest {

    // === 基本信息 ===
    @NotNull
    @JsonProperty("org_code")
    private Long orgCode; // 机构编号

    @NotNull
    @JsonProperty("user_no")
    private Long userNo; // 商户归属用户信息

    @NotBlank
    @Email
    @Size(max = 80)
    private String email; // 商户邮箱

    @NotBlank
    @Size(max = 20)
    @JsonProperty("busi_code")
    private String busiCode; // 业务类型

    @NotBlank
    @Size(max = 80)
    @JsonProperty("mer_reg_name")
    private String merRegName; // 商户注册名称

    @NotBlank
    @Size(max = 80)
    @JsonProperty("mer_type")
    private String merType; // 商户注册类型

    @NotBlank
    @Size(max = 80, min = 7)
    @JsonProperty("mer_name")
    private String merName; // 商户名称(经营名称)

    @NotBlank
    @Size(max = 80)
    @JsonProperty("mer_addr")
    private String merAddr; // 商户详细地址

    @NotBlank
    @Size(max = 16)
    @JsonProperty("province_code")
    private String provinceCode; // 省代码

    @NotBlank
    @Size(max = 16)
    @JsonProperty("city_code")
    private String cityCode; // 市代码

    @NotBlank
    @Size(max = 16)
    @JsonProperty("county_code")
    private String countyCode; // 区县代码

    @Size(max = 80)
    @JsonProperty("license_name")
    private String licenseName; // 营业执照名称（可选）

    @Size(max = 40)
    @JsonProperty("license_no")
    private String licenseNo; // 营业执照号码（可选）

    @Size(max = 10)
    @JsonProperty("license_dt_start")
    private String licenseDtStart; // 营业执照开始时间（可选）

    @Size(max = 10)
    @JsonProperty("license_dt_end")
    private String licenseDtEnd; // 营业执照过期时间（可选）

    @NotBlank
    @Size(max = 20)
    private String latitude; // 经度

    @NotBlank
    @Size(max = 20)
    private String longtude; // 纬度（字段名按文档）

    @NotBlank
    @Size(max = 20)
    private String source; // 进件来源 APP/H5

    @NotBlank
    @Size(max = 64)
    @JsonProperty("business_content")
    private String businessContent; // 商户经营内容

    @JsonProperty("is_legal_person")
    private Boolean legalPerson; // 是否法人进件（可选，不传默认法人）

    @NotBlank
    @Size(max = 20)
    @JsonProperty("lar_name")
    private String larName; // 法人姓名

    @NotBlank
    @Size(max = 8)
    @JsonProperty("lar_id_type")
    private String larIdType; // 法人证件类型

    @NotBlank
    @Size(max = 40)
    @JsonProperty("lar_id_card")
    private String larIdCard; // 法人证件号码

    @NotBlank
    @Size(max = 10)
    @JsonProperty("lar_id_card_start")
    private String larIdCardStart; // 法人证件开始日期

    @NotBlank
    @Size(max = 10)
    @JsonProperty("lar_id_card_end")
    private String larIdCardEnd; // 法人证件过期时间

    @NotBlank
    @Size(max = 20)
    @JsonProperty("contact_mobile")
    private String contactMobile; // 商户联系人手机号

    @NotBlank
    @Size(max = 32)
    @JsonProperty("contact_name")
    private String contactName; // 商户联系人姓名

    @NotBlank
    @Size(max = 20)
    @JsonProperty("openning_bank_code")
    private String openningBankCode; // 结算账户开户行号

    @NotBlank
    @Size(max = 40)
    @JsonProperty("openning_bank_name")
    private String openningBankName; // 结算账户开户行名称

    @NotBlank
    @Size(max = 20)
    @JsonProperty("clearing_bank_code")
    private String clearingBankCode; // 结算账户清算行号

    @NotBlank
    @Size(max = 20)
    @JsonProperty("settle_province_code")
    private String settleProvinceCode; // 结算信息省份代码

    @NotBlank
    @Size(max = 20)
    @JsonProperty("settle_province_name")
    private String settleProvinceName; // 结算信息省份名称

    @NotBlank
    @Size(max = 20)
    @JsonProperty("settle_city_code")
    private String settleCityCode; // 结算信息城市代码

    @NotBlank
    @Size(max = 20)
    @JsonProperty("settle_city_name")
    private String settleCityName; // 结算信息城市名称

    @NotBlank
    @Size(max = 40)
    @JsonProperty("account_no")
    private String accountNo; // 结算人银行卡号

    @NotBlank
    @Size(max = 40)
    @JsonProperty("account_name")
    private String accountName; // 结算人账户名称

    @NotBlank
    @Size(max = 8)
    @JsonProperty("account_type")
    private String accountType; // 结算账户类型

    @Size(max = 8)
    @JsonProperty("account_id_type")
    private String accountIdType; // 结算人证件类型（可选，空同法人）

    @NotBlank
    @Size(max = 8)
    @JsonProperty("account_id_card")
    private String accountIdCard; // 结算人证件号码

    @Size(max = 8)
    @JsonProperty("account_id_dt_start")
    private String accountIdDtStart; // 结算人证件开始时间（可选，空同法人）

    @Size(max = 8)
    @JsonProperty("account_id_dt_end")
    private String accountIdDtEnd; // 结算人证件过期时间（可选，空同法人）

    @Size(max = 32)
    @JsonProperty("external_no")
    private String externalNo; // 外部编号（可选）

    @NotNull
    @JsonProperty("biz_content")
    private BizContentDTO bizContent; // 业务扩展信息

    @NotNull
    @JsonProperty("attchments")
    private Set<AttachmentDTO> attchments; // 附件信息集合

    @NotBlank
    @Size(max = 10)
    @JsonProperty("settle_type")
    private String settleType; // 结算类型

    @Size(max = 20)
    @JsonProperty("shop_id")
    private String shopId; // 网点代码（可选）

    @NotBlank
    @Size(max = 20)
    @JsonProperty("settlement_type")
    private String settlementType; // 结算方式

    @JsonProperty("regular_settlement_time")
    private Integer regularSettlementTime; // 定时结算时间（可选）

    @Size(max = 80)
    @JsonProperty("contract_no")
    private String contractNo; // 电子合同编号（可选）

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_MERCHANT_ENCRY;
    }

}