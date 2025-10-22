package cn.cc.lkl.dto.merchant;

import lombok.Data;

import java.time.LocalDate;

/**
 * 电子合同参数集合说明(EC007)
 * https://o.lakala.com/#/home/document/detail?id=1169
 */
@Data
public class V3MmsOpenApiEc007 {

    /**
     * 商户类别：企业，那么读营业执照名称；如果是小微，那么读法人姓名
     */
    private String A1;
    private String A29 = "3.8%";
    private String A30 = "3.8%";

    /**
     * 年
     */
    private Integer A104;
    /**
     * 月
     */
    private Integer A105;
    /**
     * 日
     */
    private Integer A106;

    /**
     * 商户注册登记表的年
     */
    private String B1;
    /**
     * 商户注册登记表的月
     */
    private String B2;

    /**
     * 个人商户：填写法人名称，企业填写营业执照名称
     */
    private String B8;

    /**
     * 网站地址
     * 互联网业务填写
     */
    private String B11;
    /**
     * 商户简称（互联网支付）
     * 互联网业务填写
     */
    private String B12;
    /**
     * 交易Ip地址
     * 互联网业务填写
     */
    private String B15;

    /**
     * 办公地址
     */
    private String B13;

    /**
     * 个人商户留空，企业填写营业执照证件代码
     */
    private String B14;

    /**
     * 对公结算勾选，读取结算信息里面的银行卡号
     * 结算账户名称：同工商注册名称选择
     */
    private String B16;
    /**
     * todo 怎么勾选
     * 对私结算填写，读取结算信息里面的银行卡号
     * 结算账户名称：其他名称选择
     */
    private String B17;
    /**
     * 对私结算填写，其他名称填写
     */
    private String B18;

    /**
     * 开户行（含支行）
     */
    private String B19;

    /**
     * 银行卡号
     */
    private String B20;

    /**
     * 法人姓名
     */
    private String B24;
    /**
     * 法人证件号码
     */
    private String B25;
    /**
     *法人手机
     */
    private String B26;
    /**
     * 联系人姓名
     */
    private String B27;
    /**
     * 联系人邮箱
     */
    private String B28;

    public V3MmsOpenApiEc007() {
        // 分别获取年月日
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 获取年
        A104 = today.getYear();
        // 获取月（1-12）
        A105 = today.getMonthValue();
        // 获取日（1-31）
        A106 = today.getDayOfMonth();

    }
}

