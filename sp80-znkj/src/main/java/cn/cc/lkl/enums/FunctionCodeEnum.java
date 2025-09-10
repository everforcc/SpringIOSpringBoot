package cn.cc.lkl.enums;

/**
 * lkl请求枚举
 * SDK 不包含的，自定义
 */
public enum FunctionCodeEnum {


    // 商户进件
    API_V3_TKBS_MERCHANT_ENCRY("/api/v3/tkbs/merchant_encry", "新增商户进件"),
    API_V3_MMS_OPEN_API_EC_APPLY("/api/v3/mms/open_api/ec/apply", "电子合同申请"),
    API_V3_TKBS_CUSTOMER_FILE_UPLOAD("/api/v3/tkbs/customer/file/upload", "商户进件文件上传"),

    // 进件相关信息查询
    API_V3_TKBS_ORGANIZATION_PARENT_CODE("/api/v3/tkbs/organization_parent_code", "获取地区信息"),

    // 商户信息变更
    API_V3_TKBS_CUSTOMER_UPDATE_REVIEW("/api/v3/tkbs/customer_update_review", "商户审核状态查询"),

    // 商户信息查询
    API_V3_TKBS_OPEN_MERCHANT_INFO("/api/v3/tkbs/open_merchant_info", "获取商户信息"),
    API_V3_TKBS_OPEN_MERCHANT_SUBMER("/api/v3/tkbs/open_merchant_submer", "新子商户查询"),

    // 入网服务
    API_V2_MMS_OPENAPI_REALNAME_SAVEALIPAYCONTACTINFO("/api/v2/mms/openApi/realName/saveAlipayContactInfo", "支付宝实名联系人信息保存"),
    API_V2_MMS_OPENAPI_REALNAME_SAVECONTACTINFO("/api/v2/mms/openApi/realName/saveContactInfo", "微信实名联系人信息保存"),

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
