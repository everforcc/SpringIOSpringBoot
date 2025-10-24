package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 电子合同参数集合说明(EC007)
 * https://o.lakala.com/#/home/document/detail?id=1169
 * V3MmsOpenApiEcQStatusRequest
 */
@Data
public class V3MmsOpenApiEc007 {

    /**
     * 商户类别：企业，那么读营业执照名称；如果是小微，那么读法人姓名
     */
    @JsonProperty("A1")
    private String A1;
    @JsonProperty("A29")
    private String A29 = "3.8%";
    @JsonProperty("A30")
    private String A30 = "3.8%";

    /**
     * 年
     */
    @JsonProperty("A104")
    private Integer A104;
    /**
     * 月
     */
    @JsonProperty("A105")
    private Integer A105;
    /**
     * 日
     */
    @JsonProperty("A106")
    private Integer A106;

    /**
     * 商户注册登记表的年
     */
    @JsonProperty("B1")
    private String B1;
    /**
     * 商户注册登记表的月
     */
    @JsonProperty("B2")
    private String B2;

    /**
     * 个人商户：填写法人名称，企业填写营业执照名称
     */
    @JsonProperty("B8")
    private String B8;

    /**
     * 网站地址
     * 互联网业务填写
     */
    @JsonProperty("B11")
    private String B11;
    /**
     * 商户简称（互联网支付）
     * 互联网业务填写
     */
    @JsonProperty("B12")
    private String B12;
    /**
     * 交易Ip地址
     * 互联网业务填写
     */
    @JsonProperty("B15")
    private String B15;

    /**
     * 办公地址
     */
    @JsonProperty("B13")
    private String B13;

    /**
     * 个人商户留空，企业填写营业执照证件代码
     */
    @JsonProperty("B14")
    private String B14;

    /**
     * 对公结算勾选，读取结算信息里面的银行卡号
     * 结算账户名称：同工商注册名称选择
     */
    @JsonProperty("B16")
    private String B16;
    /**
     * 怎么勾选 填 是
     * 对私结算填写，读取结算信息里面的银行卡号
     * 结算账户名称：其他名称选择
     */
    @JsonProperty("B17")
    private String B17;
    /**
     * 对私结算填写，其他 名称填写
     */
    @JsonProperty("B18")
    private String B18;

    /**
     * 开户行（含支行）
     */
    @JsonProperty("B19")
    private String B19;

    /**
     * 银行卡号
     */
    @JsonProperty("B20")
    private String B20;

    /**
     * 必填，法人姓名
     */
    @JsonProperty("B24")
    private String B24;
    /**
     * 必填，法人证件号码
     */
    @JsonProperty("B25")
    private String B25;
    /**
     * 必填，法人手机
     */
    @JsonProperty("B26")
    private String B26;
    /**
     * 必填，联系人姓名
     */
    @JsonProperty("B27")
    private String B27;
    /**
     * 必填，联系人邮箱
     */
    @JsonProperty("B28")
    private String B28;
    /**
     * 必填，联系人证件号码
     */
    @JsonProperty("B29")
    private String B29;
    /**
     * 必填，联系人手机号
     */
    @JsonProperty("B30")
    private String B30;
    /**
     * 必填，门店信息里面的门店名称
     */
    @JsonProperty("B31")
    private String B31;
    /**
     * 必填，门店信息里面的门店联系人
     */
    @JsonProperty("B32")
    private String B32;
    /**
     * 终端布防地址（中英文），门店地址
     */
    @JsonProperty("B33")
    private String B33;
    /**
     * 必填，门店信息里面的联系手机号
     */
    @JsonProperty("B34")
    private String B34;
    /**
     * 必填，读取结算信息里面的开户支行
     */
    @JsonProperty("D1")
    private String D1;
    /**
     * 必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("D2")
    private String D2;
    /**
     * 必填，读取法人基本信息的姓名
     */
    @JsonProperty("D3")
    private String D3;
    /**
     * 必填，河南中南科技有限公司
     */
    @JsonProperty("D4")
    private String D4;

    @JsonProperty("D5")
    private String D5;
    /**
     * 必填，读取法人基本信息的姓名
     */
    @JsonProperty("D6")
    private String D6;
    /**
     * 必填，必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("D7")
    private String D7;
    /**
     * 必填，读取法人基本信息的姓名
     */
    @JsonProperty("D8")
    private String D8;
    /**
     * 必填，必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("D9")
    private String D9;
    /**
     * 必填，读取法人基本信息的姓名
     */
    @JsonProperty("D10")
    private String D10;
    /**
     * 必填，必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("D11")
    private String D11;
    /**
     * 必填，必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("D12")
    private String D12;
    /**
     * 默认是河南中南科技有限公司
     */
    @JsonProperty("E1")
    private String E1;
    /**
     * 收款方与分账方合作协议
     */
    @JsonProperty("E2")
    private String E2;
    /**
     * 收款方与分账方合作协议
     * 1 订单
     * 2 余额
     */
    @JsonProperty("E3")
    private String E3;
    /**
     * E3选择第1种时，必填，10%
     */
    @JsonProperty("E4")
    private String E4;
    /**
     * 必填，必填，PC端点击链接，点击确定时，获取服务器的时间
     */
    @JsonProperty("E7")
    private String E7;
    /**
     * 结算授权委托书：法人代表或结算账户所属人名字
     */
    @JsonProperty("E8")
    private String E8;

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
        D2 = today.toString();
        D4 = "河南中南科技有限公司";
        D5 = "13022110823";
        D7 = today.toString();
        D9 = today.toString();
        D11 = today.toString();
        D12 = today.toString();
        E1 = "河南中南科技有限公司";
        E2 = "特约商户支付服务合作协议V4.1+分账结算授权委托书";
        E3 = "1";
        E4 = "10";
        E7 = today.toString();
    }
}

