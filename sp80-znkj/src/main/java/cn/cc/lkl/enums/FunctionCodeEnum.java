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
