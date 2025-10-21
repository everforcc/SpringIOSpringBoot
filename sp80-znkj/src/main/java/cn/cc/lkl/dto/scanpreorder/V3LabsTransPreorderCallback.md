## **请求报文**

| **字段名**                | **中文名称**             | **是否必填** | **类型**     | **说明**                                                     |
| :------------------------ | :----------------------- | :----------- | :----------- | :----------------------------------------------------------- |
| merchant_no               | 商户号                   | M            | String(32)   | 拉卡拉分配的商户号（交易请求接口中商户号）                   |
| out_trade_no              | 商户交易流水号           | M            | String(64)   |                                                              |
| trade_no                  | 拉卡拉交易流水号         | M            | String(32)   | 拉卡拉交易流水号                                             |
| log_no                    | 拉卡拉对账单流水号       | M            | String(14)   | trade_no的后14位                                             |
| acc_trade_no              | 账户端交易订单号         | M            | String(32)   | 账户端交易订单号                                             |
| account_type              | 钱包类型                 | M            | String(32)   | 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING 数字人民币-DCPAY |
| settle_merchant_no        | 结算商户号               | C            | String(32)   | 拉卡拉分配的商户号                                           |
| settle_term_no            | 结算终端号               | C            | String(32)   | 拉卡拉分配的业务终端号                                       |
| trade_status              | 交易状态                 | M            | String(16)   | INIT-初始化 CREATE-下单成功 SUCCESS-交易成功 FAIL-交易失败 DEAL-交易处理中 UNKNOWN-未知状态 CLOSE-订单关闭 PART_REFUND-部分退款 REFUND-全部退款 REVOKED-订单撤销 |
| total_amount              | 订单金额                 | M            | String(12)   | 单位分，整数数字型字符                                       |
| payer_amount              | 付款人实付金额           | C            | String(12)   | 付款人实付金额，单位分                                       |
| acc_settle_amount         | 账户端结算金额           | C            | String(12)   | 账户端应结订单金额，单位分 ，账户端应结订单金额=付款人实际发生金额+账户端优惠金额 |
| acc_mdiscount_amount      | 商户侧优惠金额（账户端） | C            | String(12)   | 商户优惠金额，单位分                                         |
| acc_discount_amount       | 账户端优惠金额           | C            | String(12)   | 账户端优惠金额，单位分                                       |
| acc_other_discount_amount | 账户端其它优惠金额       | C            | String(12)   | 账户端返回账户端其它优惠金额，单位分                         |
| trade_time                | 交易完成时间             | C            | String(14)   | 实际支付时间。yyyyMMddHHmmss                                 |
| user_id1                  | 用户标识1                | C            | String(128)  | 微信sub_open_id, 支付宝buyer_logon_id（买家支付宝账号）      |
| user_id2                  | 用户标识2                | C            | String(128)  | 微信openId,支付宝buyer_user_id,银联user_id                   |
| acc_activity_id           | 活动 ID                  | C            | String(32)   | 在账户端商户后台配置的批次 ID                                |
| bank_type                 | 付款银行                 | C            | String(128)  | 付款银行                                                     |
| card_type                 | 银行卡类型               | C            | String(16)   | 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知 sha |
| remark                    | 备注                     | C            | String(128)  |                                                              |
| hb_fq_pay_info            | 花呗分期支付信息         | C            | Object       |                                                              |
| sub_mch_id                | 子商户号                 | C            | String(20)   | 账户端子商户号                                               |
| out_split_rsp_infos       | 合单信息                 | C            | List<>       |                                                              |
| discount_goods_detail     | 单品券优惠的商品优惠信息 | C            | String(1024) |                                                              |

### **花呗分期支付信息hb_fq_pay_info内容**

| **字段名**       | **中文名称** | **是否必填** | **类型**   | **说明**                 |
| :--------------- | :----------- | :----------- | :--------- | :----------------------- |
| fq_amount        | 分期金额     | M            | String(12) | 单位分，整数数字型字符   |
| user_install_num | 分期期数     | M            | String(12) | 分期期数，整数数字型字符 |

### **合单信息out_split_rsp_infos内容**

| **字段名**       | **中文名称**     | **是否必填** | **类型**   | **说明** |
| :--------------- | :--------------- | :----------- | :--------- | :------- |
| sub_trade_no     | 子单拉卡拉流水号 | M            | String(32) |          |
| sub_log_no       | 子单对账流水号   | M            | String(12) |          |
| out_sub_trade_no | 子单外部流水号   | M            | String(32) |          |
| merchant_no      | 子单商户号       | M            | String(32) |          |
| term_no          | 子单终端号       | M            | String(8)  |          |
| amount           | 子单金额         | M            | String(12) | 单位：分 |

### **discount_goods_detail字段内容**

| **字段名**      | **中文名称** | **是否必填** | **类型** | **说明** |
| :-------------- | :----------- | :----------- | :------- | :------- |
| goods_id        | 商品id       | M            | String   |          |
| goods_name      | 商品名称     | C            | String   |          |
| discount_amount | 优惠金额     | C            | String   |          |
| voucher_id      | 优惠id       | C            | String   |          |