package cn.cc.lkl.enums;

/**
 * lkl请求枚举
 */
public enum FunctionCodeEnum {

    /**
     * 商户统一变更接口(2022)
     */
    API_V3_TKBS_MERCHANT_ENCRY("/api/v3/tkbs/merchant_encry", "新增商户进件"),
    API_V3_TKBS_ORGANIZATION_PARENT_CODE("/api/v3/tkbs/organization_parent_code", "获取地区信息"),
    API_V3_TKBS_CUSTOMER_FILE_UPLOAD("/api/v3/tkbs/customer/file/upload", "商户进件文件上传"),
    API_V3_TKBS_OPEN_MERCHANT_INFO("/api/v3/tkbs/open_merchant_info", "获取商户信息"),
    API_V3_TKBS_CUSTOMER_UPDATE_REVIEW("/api/v3/tkbs/customer_update_review", "商户审核状态查询"),

    API_V3_MMS_OPEN_API_EC_APPLY("/api/v3/mms/open_api/ec/apply", "电子合同申请"),

    // 微信实名认证
    // 支付宝实名认证
    // 微信支付宝认证结果查询

    ;

    private final String code;

    private final String name;

    FunctionCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return this.code.replaceAll("\\.", "/");
    }

}
