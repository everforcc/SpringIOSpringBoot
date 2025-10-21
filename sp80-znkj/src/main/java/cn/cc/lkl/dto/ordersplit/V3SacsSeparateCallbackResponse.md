### **请求报文（系统发起，商户为接收方）**

| 字段名称        | 是否必填 | 字段类型 | 长度 | 字段描述           | 取值说明                                                     |
| :-------------- | :------- | :------- | :--- | :----------------- | :----------------------------------------------------------- |
| separate_no     | M        | String   | 32   | 分账指令流水号     | 请求透返                                                     |
| out_separate_no | M        | String   | 32   | 商户分账指令流水号 | 请求透返                                                     |
| cmd_type        | M        | String   | 32   | 指令类型           | SEPARATE：分账 CANCEL：分账撤销 FALLBACK：分账回退           |
| log_no          | C        | String   | 14   | 拉卡拉对账单流水号 |                                                              |
| log_date        | C        | String   | 8    | 交易日期           | posp日期，yyyyMMdd，查清结算用                               |
| cal_type        | C        | String   | 2    | 分账计算类型       | 0 按照指定金额。1 按照指定比例，默认 0                       |
| separate_type   | C        | String   | 4    | 分账接收类型       | 0 全部分账到商户本身。1 分账到多方，默认 1                   |
| separate_date   | C        | String   | 8    | 分账日期           | yyyyMMdd                                                     |
| finish_date     | C        | String   | 8    | 完成日期           | yyyyMMdd                                                     |
| total_amt       | C        | String   | 15   | 发生总金额         | [单位：分](http://106.14.112.9:5000/wiki/docs/openplatform/openplatform-1ds5kldhqk7k7) |
| status          | M        | String   | 32   | 分账状态           | ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功, CANCELING:撤销中, CANCELED:撤销成功, CANCEL_FAIL:撤销失败, FALLBACKING:回退中, FALLBACK_END:回退结束 |
| final_status    | M        | String   | 32   | 处理状态           | ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功  |
| detail_datas    | C        | List     |      | 明细数据           |                                                              |

##### detail_datas

| 字段名称         | 是否必填 | 字段类型 | 长度 | 字段描述     | 取值说明 |
| :--------------- | :------- | :------- | :--- | :----------- | :------- |
| recv_merchant_no | C        | String   | 32   | 接收方商户号 |          |
| recv_no          | C        | String   | 32   | 接收方编号   |          |
| amt              | C        | String   | 32   | 分账金额     |          |