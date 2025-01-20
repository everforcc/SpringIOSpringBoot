package cn.cc.huifu.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品系统
 * znkj_huifu_info
 */
@Data
public class HuiFuInfo {

    private static final long serialVersionUID = -768907006813874631L;

    /**
     * 搜索值
     */
    @JsonIgnore
    private String searchValue;

    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateId;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记（0代表存在 1代表删除）
     */
    private Integer delFlag;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

    /**
     * 请求流水号
     */
    private String reqSeqId;

    /**
     * 请求时间
     */
    private String reqDate;
    /**
     * 商户号
     */
    private String huifuId;
    /**
     * 商品描述（自行添加的）
     */
    private String goodsDesc;
    /**
     * 商品ID（自行添加的）
     */
    private String goodsId;
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 交易金额
     */
    private String transAmt;
    /**
     * 付款订单-有效期（请求参数中的）
     */
    private String timeExpire;
    /**
     * ATU真实商户号
     */
    private String atuSubMerId;
    /**
     * 通道返回码
     */
    private String bankCode;
    /**
     * 通道返回描述
     */
    private String bankMessage;
    /**
     * 全局流水号
     */
    private String hfSeqId;

    private String isCleanSplit;
    /**
     * 用户账单上的商户订单号
     */
    private String partyOrderId;
    /**
     * 二维码链接
     */
    private String qrCode;
    /**
     * 备注
     */
    private String remark1;
    /**
     * 业务响应码
     */
    private String respCode;
    /**
     * 业务响应信息
     */
    private String respDesc;
    /**
     * 交易状态
     */
    private String transStat;
    /**
     * 待确认金额
     */
    private String unConfirmAmt;
    /**
     * 订单类型 1:金币充值2:租赁
     */
    private String orderType;

}
