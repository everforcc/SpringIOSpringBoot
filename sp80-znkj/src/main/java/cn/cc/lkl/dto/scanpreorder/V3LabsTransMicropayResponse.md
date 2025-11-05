| **字段名**                | **中文名称**           | **是否必填** | **类型**    | **说明**                                                     |
| :------------------------ | :--------------------- | :----------- | :---------- | :----------------------------------------------------------- |
| need_query                | 是否需要发起查询       | M            | String(32)  | 0=不需要 1=需要 当返回1时，代表订单处理中，商户需主动发起查询 |
| merchant_no               | 商户号                 | M            | String(32)  | 拉卡拉分配的商户号（请求接口中商户号）                       |
| out_trade_no              | 商户交易流水号         | M            | String(32)  | 请求报文中的商户交易流水号                                   |
| trade_no                  | 拉卡拉交易流水号       | M            | String(32)  | 拉卡拉交易流水号                                             |
| log_no                    | 拉卡拉对账单流水号     | M            | String(14)  | 拉卡拉对账单流水号                                           |
| acc_trade_no              | 账户端交易订单号       | C            | String(32)  | 账户端交易流水号                                             |
| account_type              | 钱包类型               | M            | String(16)  | 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 数字货币：DCPAY |
| total_amount              | 订单金额               | M            | String(12)  | 单位分，整数数字型字符 订单金额=付款人实际发生金额+商户优惠金额+账户端优惠金额 |
| payer_amount              | 付款人实际发生金额     | M            | String(12)  |                                                              |
| acc_settle_amount         | 账户端应结订单金额     | M            | String(12)  | 应结订单金额，单位分 ，账户端应结订单金额=付款人实际发生金额+账户端优惠金额 |
| acc_mdiscount_amount      | 商户优惠金额（账户端） | C            | String(12)  | 账户端返回商户优惠金额，单位分                               |
| acc_discount_amount       | 账户端优惠金额         | C            | String(12)  | 账户端返回账户端优惠金额，单位分                             |
| acc_other_discount_amount | 账户端其它优惠金额     | C            | String(12)  | 账户端返回账户端其它优惠金额，单位分                         |
| trade_time                | 交易完成时间           | M            | String(14)  | 以账户端返回时间为准                                         |
| bank_type                 | 付款银行               | C            | String(128) | 付款银行                                                     |
| card_type                 | 银行卡类型             | C            | String(16)  | 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知 |
| remark                    | 备注                   | C            | String(128) |                                                              |
| acc_resp_fields           | 账户端返回信息域       | C            | Object      | 账户端返回信息域                                             |