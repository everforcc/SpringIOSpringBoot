package cn.cc.lkl.enums;

public enum FunctionCodeEnum {

    /**
     * 商户统一变更接口(2022)
     */
    API_V3_TKBS_MERCHANT_ENCRY("/api/v3/tkbs/merchant_encry", "新增商户进件"),
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

}
