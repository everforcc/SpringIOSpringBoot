package cn.cc.lkl.dto.merchantquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取商户信息
 * https://o.lakala.com/p/#/document/detail?id=1089
 * 返回值
 */
@Data
public class V3TkbsOpenMerchantInfoResponse {

    /**
     * 商户基础信息
     */
    private Customer customer;

    /**
     * 商户费率信息
     */
    @JsonProperty("customer_fee")
    private List<CustomerFee> customerFee;

    /**
     * 外部编号
     */
    @JsonProperty("external_no")
    private String externalNo;

    /**
     * 商户终端信息，绑定才有
     */
    private Pos pos;

    /**
     * 已有产品信息
     */
    @JsonProperty("product_vos")
    private List<CustomerProductVo> productVos;

    /**
     * 商户结算信息
     */
    @JsonProperty("settle_card")
    private CustomerSettleCard settleCard;

    /**
     * 网点信息
     */
    @JsonProperty("shop_info_list")
    private List<ShopInfo> shopInfoList;

    /**
     * 终端信息集合
     */
    @JsonProperty("terminal_info")
    private List<TerminalInfo> terminalInfo;


    /**
     * 商户基础信息
     */
    @Data
    public static class Customer {
        /**
         * 激活码
         */
        private String active_no;

        /**
         * 激活时间
         */
        @JsonProperty("activity_time")
        private String activityTime;

        /**
         * 机构号
         */
        @JsonProperty("agency_no")
        private Long agencyNo;

        /**
         * 服务商编号
         */
        @JsonProperty("agent_no")
        private Long agentNo;

        /**
         * 协议签署状态
         */
        @JsonProperty("agreement_status")
        private String agreementStatus;

        /**
         * 商户经营范围
         */
        @JsonProperty("biz_content")
        private String bizContent;

        /**
         * POS类型
         */
        @JsonProperty("bz_pos")
        private String bzPos;

        /**
         * 商户类型 TP_MERCHANT:企业，TP_PERSONAL:小微
         */
        @JsonProperty("channel_type")
        private String channelType;

        /**
         * 撤机时间
         */
        @JsonProperty("close_time")
        private String closeTime;

        /**
         * 联系人改名
         */
        @JsonProperty("contact_man_name")
        private String contactManName;

        /**
         * 创建时间
         */
        @JsonProperty("create_time")
        private String createTime;

        /**
         * 商户名称
         */
        @JsonProperty("customer_name")
        private String customerName;

        /**
         * 商户编号（拓客系统商户号）
         */
        @JsonProperty("customer_no")
        private Long customerNo;

        /**
         * 商户状态 OPEN:开通，CLOSE:关闭，LOSS:流失,WAIT_AUDI:审核中,REJECT:审核拒绝,REVIEW_FAIL:复核失败,REVIEW_AUDIT:复核审核中,ACTIVITY:激活
         */
        @JsonProperty("customer_status")
        private String customerStatus;

        /**
         * 拒绝原因 商户状态为REJECT返回
         */
        @JsonProperty("audit_remark")
        private String auditRemark;

        /**
         * 外部商户编号（银联822商户号）
         */
        @JsonProperty("merchant_no")
        private String merchantNo;

        /**
         * 商户身份证号
         */
        @JsonProperty("identity_no")
        private String identityNo;

        /**
         * 商户身份证号有效期
         */
        @JsonProperty("identity_no_expire")
        private String identityNoExpire;

        /**
         * 是否达标 TRUE：是 FALSE：否
         */
        @JsonProperty("is_standard")
        private String isStandard;

        /**
         * 法人姓名
         */
        @JsonProperty("legal_name")
        private String legalName;

        /**
         * 营业执照号
         */
        @JsonProperty("license_no")
        private String licenseNo;

        /**
         * 邮箱
         */
        private String mailbox;

        /**
         * 商户类别码
         */
        @JsonProperty("mcc_code")
        private String mccCode;

        /**
         * 营业执照号有效期
         */
        @JsonProperty("mer_license_expire")
        private String merLicenseExpire;

        /**
         * 企业名称
         */
        @JsonProperty("mer_name")
        private String merName;

        /**
         * 商户来源 AGENT:服务商平台，APP:APP平台，H5:H5平台
         */
        @JsonProperty("merchant_source")
        private String merchantSource;

        /**
         * 入网时间
         */
        @JsonProperty("open_time")
        private String openTime;

        /**
         * 乐观锁版本号
         */
        private Integer optimistic;

        /**
         * 商户手机号
         */
        @JsonProperty("phone_no")
        private String phoneNo;

        /**
         * 平台标识
         */
        private String platform;

        /**
         * 机具序列号
         */
        @JsonProperty("pos_sn")
        private String posSn;

        /**
         * 位置信息
         */
        private String position;

        /**
         * 刷够返奖励模式 TO_MERCHANT,TO_AGENT
         */
        @JsonProperty("reward_mode")
        private String rewardMode;

        /**
         * 提现费等固定费用
         */
        @JsonProperty("standard_fee")
        private Double standardFee;

        /**
         * 达标时间
         */
        @JsonProperty("standard_time")
        private String standardTime;

        /**
         * 虚拟终端号
         */
        @JsonProperty("term_no")
        private String termNo;

        /**
         * 终端数量
         */
        @JsonProperty("term_num")
        private Integer termNum;

        /**
         * 更新时间
         */
        @JsonProperty("update_time")
        private String updateTime;

        /**
         * 云闪付小额优惠[FALSE:否,TRUE:是]
         */
        @JsonProperty("ysf_discount")
        private String ysfDiscount;

        /**
         * 省份名称
         */
        @JsonProperty("province_name")
        private String provinceName;

        /**
         * 城市名称
         */
        @JsonProperty("city_name")
        private String cityName;

        /**
         * 区名称
         */
        @JsonProperty("county_name")
        private String countyName;

        /**
         * 详细地址
         */
        @JsonProperty("receive_detail")
        private String receiveDetail;
    }

    /**
     * 商户费率信息
     */
    @Data
    public static class CustomerFee {
        /**
         * 卡类型 CREDIT_CARD,QR_CORD,DEBIT_CARD,YSF_LE_1000_CREDIT,YSF_LE_1000_DEBIT
         */
        @JsonProperty("card_type")
        private String cardType;

        /**
         * 创建时间
         */
        private String create_time;

        /**
         * 商户编号
         */
        @JsonProperty("customer_no")
        private Long customerNo;

        /**
         * 日限额
         */
        @JsonProperty("day_limit")
        private Double dayLimit;

        /**
         * 商户号
         */
        private String merchant_no;

        /**
         * 费率
         */
        @JsonProperty("fee_rate")
        private Double feeRate;

        /**
         * ID
         */
        private Long id;

        /**
         * 手续费最多值
         */
        @JsonProperty("max_amt")
        private Double maxAmt;

        /**
         * 手续费最小值
         */
        @JsonProperty("min_amt")
        private Double minAmt;

        /**
         * 月限额
         */
        @JsonProperty("month_limit")
        private Double monthLimit;

        /**
         * 乐观锁版本号
         */
        private Integer optimistic;

        /**
         * 单笔限额
         */
        @JsonProperty("per_limi")
        private Double perLimi;

        /**
         * 秒到手续费
         */
        @JsonProperty("second_fee")
        private Double secondFee;

        /**
         * 结算类型 D0,D1,T1
         */
        @JsonProperty("trans_settle_type")
        private String transSettleType;

        /**
         * 交易类型
         */
        @JsonProperty("trans_type")
        private String transType;

        /**
         * 终端号（根据trans_type判断是刷卡还是扫码的终端号信息）
         */
        @JsonProperty("trem_no")
        private String tremNo;

        /**
         * 更新时间
         */
        private String update_time;

        /**
         * 商户管理费费率
         */
        @JsonProperty("customer_management_fee")
        private Double customerManagementFee;

        /**
         * 商户管理费d
         */
        @JsonProperty("customer_managementd_fee")
        private Double customerManagementdFee;

        /**
         * 商户管理费e
         */
        @JsonProperty("customer_managemente_fee")
        private Double customerManagementeFee;

        /**
         * 商户管理费f
         */
        @JsonProperty("customer_managementf_fee")
        private Double customerManagementfFee;
    }

    /**
     * POS终端信息
     */
    @Data
    public static class Pos {
        /**
         * 激活码
         */
        @JsonProperty("active_no")
        private String activeNo;

        /**
         * 活动标识
         */
        @JsonProperty("activity_flag")
        private String activityFlag;

        /**
         * 开通时间
         */
        @JsonProperty("open_time")
        private String openTime;

        /**
         * 终端序列号
         */
        @JsonProperty("pos_sn")
        private String posSn;

        /**
         * 终端类型
         */
        @JsonProperty("pos_type")
        private String posType;

        /**
         * 拉卡拉虚拟终端号
         */
        @JsonProperty("term_no")
        private String termNo;
    }

    /**
     * 商户结算信息
     */
    @Data
    public static class CustomerSettleCard {
        /**
         * 可用状态 ENABLE,DISABLE
         */
        @JsonProperty("able_status")
        private String ableStatus;

        /**
         * 账户性质。57：对公 58：对私
         */
        @JsonProperty("account_kind")
        private String accountKind;

        /**
         * 开户名
         */
        @JsonProperty("account_name")
        private String accountName;

        /**
         * 开户账号
         */
        @JsonProperty("account_no")
        private String accountNo;

        /**
         * 审核状态
         */
        @JsonProperty("audit_status")
        private String auditStatus;

        /**
         * 开户行名称
         */
        @JsonProperty("bank_name")
        private String bankName;

        /**
         * 开户行编号
         */
        @JsonProperty("bank_no")
        private String bankNo;

        /**
         * 城市编号
         */
        private String city_code;

        /**
         * 城市名称
         */
        private String city_name;

        /**
         * 清算行号
         */
        @JsonProperty("clearing_bank_no")
        private String clearingBankNo;

        /**
         * 区编号
         */
        @JsonProperty("county_code")
        private String countyCode;

        /**
         * 区名称
         */
        @JsonProperty("county_name")
        private String countyName;

        /**
         * 创建时间
         */
        private String create_time;

        /**
         * 商户号
         */
        private String merchant_no;

        /**
         * ID
         */
        private Long id;

        /**
         * 身份证号
         */
        private String id_card;

        /**
         * 乐观锁版本号
         */
        private Integer optimistic;

        /**
         * 所有者
         */
        @JsonProperty("own_no")
        private String ownNo;

        /**
         * 省份编号
         */
        @JsonProperty("province_code")
        private String provinceCode;

        /**
         * 省份名称
         */
        @JsonProperty("province_name")
        private String provinceName;

        /**
         * 更新时间
         */
        @JsonProperty("update_time")
        private String updateTime;

        /**
         * 验证状态
         */
        private String validate;
    }

    /**
     * 终端信息
     */
    @Data
    public static class TerminalInfo {
        /**
         * 三代终端编号
         */
        @JsonProperty("core_term_id")
        private Long coreTermId;

        /**
         * 终端分类
         */
        @JsonProperty("term_type_code")
        private Integer termTypeCode;

        /**
         * 终端分类名称
         */
        @JsonProperty("term_type_name")
        private String termTypeName;

        /**
         * 终端号
         */
        @JsonProperty("term_no_list")
        private List<String> termNoList;

        /**
         * 激活码
         */
        @JsonProperty("active_no_vo_list")
        private List<ActiveNoVo> activeNoVoList;
    }

    /**
     * 网点信息
     */
    @Data
    public static class ShopInfo {
        /**
         * 网点编号
         */
        private Long shop_id;

        /**
         * 门店名称
         */
        private String shop_name;

        /**
         * 门店地址
         */
        @JsonProperty("shop_address")
        private String shopAddress;

        /**
         * 省份编号
         */
        @JsonProperty("province_code")
        private String provinceCode;

        /**
         * 省份名称
         */
        @JsonProperty("province_name")
        private String provinceName;

        /**
         * 城市编号
         */
        @JsonProperty("city_code")
        private String cityCode;

        /**
         * 城市名称
         */
        @JsonProperty("city_name")
        private String cityName;

        /**
         * 区编号
         */
        @JsonProperty("shop_dist_code")
        private String shopDistCode;

        /**
         * 区名称
         */
        @JsonProperty("shop_dist_name")
        private String shopDistName;

        /**
         * 联系人
         */
        @JsonProperty("shop_contact_name")
        private String shopContactName;

        /**
         * 联系人手机
         */
        @JsonProperty("shop_contact_mobile")
        private String shopContactMobile;
    }

    /**
     * 激活码信息
     */
    @Data
    public static class ActiveNoVo {
        /**
         * 业务类别码 BANK_CARD:银行卡, QR_CODE_CARD:扫码
         */
        @JsonProperty("busi_type_code")
        private String busiTypeCode;

        /**
         * 终端号
         */
        @JsonProperty("term_no")
        private String termNo;

        /**
         * 终端激活码
         */
        @JsonProperty("active_no")
        private String activeNo;
    }

    /**
     * 商户产品信息
     */
    @Data
    public static class CustomerProductVo {
        /**
         * 商户产品
         */
        private String product;

        /**
         * 商户产品名称
         */
        @JsonProperty("product_name")
        private String productName;
    }
}
