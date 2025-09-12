## **请求参数**

| **属性**              | **说明**                  | **必选** | **类型**    | **备注**                                                     |
| :-------------------- | :------------------------ | :------- | :---------- | :----------------------------------------------------------- |
| order_no              | 四方机构自定义订单编号    | M        | String(32)  | 建议：平台编号+14位年月日时（24小时制）分秒+8位的随机数（同一接入机构不重复） |
| org_id                | 机构号                    | M        | Integer     | 签约方所属拉卡拉机构                                         |
| ec_type_code          | 合同类别                  | M        | String(12)  | EC001 : 特约商户支付服务合作协议V3.1(商户入网) EC002 : 特约商户支付服务合作协议V3.2（商户入网+分账） EC003 : 分账结算授权委托书 EC004 : 特约商户支付服务合作协议V3.3（商户入网） EC005 : 特约商户支付服务合作协议V3.3（商户入网+分账）EC007 : 特约商户支付服务合作协议V4.1 + 结算授权委托书 (商户入网 + 分账 ) EC008 : 特约商户支付服务合作协议V4.1 (商户入网)EC009 : 结算授权委托书 |
| cert_type             | 法人/经营者证件类型       | M        | String(16)  | RESIDENT_ID（身份证）； PASSPORT（护照）； HK_MACAO_PASS（港澳居民往来内地通行证）； TAIWAN_PASS（台湾居民来往大陆通行证）； |
| cert_name             | 法人/经营者姓名           | M        | String(32)  |                                                              |
| cert_no               | 法人/经营者证件号码       | M        | String(32)  |                                                              |
| mobile                | 签约手机号                | M        | String(16)  | 1.小微个人商户（无营业执照），签约手机号必须填写商户经营者本人手机号；2.个体工商户或企业商户（有营业执照），签约手机号必须填写法人手机号或者经办人手机号 **合同签署人手机号，请慎重填写，不可修改** |
| business_license_no   | 营业执照号                | C        | String(32)  | **个体工商户或企业商户 必传**                                |
| business_license_name | 营业执照名称              | C        | String(32)  | **个体工商户或企业商户 必传**                                |
| openning_bank_code    | 企业/经营者结算开户行号   | M        | String(32)  |                                                              |
| openning_bank_name    | 企业/经营者结算开户行名称 | M        | String(128) |                                                              |
| acct_type_code        | 企业/经营者结算卡性质     | M        | String(2)   | 57 对公、 58 对私                                            |
| acct_no               | 企业/经营者结算卡号       | M        | String(32)  |                                                              |
| acct_name             | 企业/经营者结算卡名称     | M        | String(64)  | 企业/经营者结算卡名称                                        |
| ec_content_parameters | 电子合同内容参数集合      | M        | JSONString  | 按合同类型（ecTypeCode）传递不同的参数集合，[EC001合同参数说明](https://o.lakala.com/#/home/document/detail?id=290)[EC002合同参数说明](https://o.lakala.com/#/home/document/detail?id=291)[EC003合同参数说明](https://o.lakala.com/#/home/document/detail?id=292)[EC004合同参数说明](https://o.lakala.com/#/home/document/detail?id=377)[EC005合同参数说明](https://o.lakala.com/#/home/document/detail?id=499)[EC007合同参数说明](https://o.lakala.com/#/home/document/detail?id=1169)[EC008合同参数说明](https://o.lakala.com/#/home/document/detail?id=1170)[EC009合同参数说明](https://o.lakala.com/#/home/document/detail?id=1171) |
| agent_tag             | 是否经办签约              | C        | Integer(1)  | 0 不启用 1启用 ； 缺省 0                                     |
| agent_name            | 经办人名称                | C        | String(32)  | 经办人名称（要与证件号对应）agentTag 为1时 必传              |
| agent_cert_type       | 经办人证件类型            | C        | String(32)  | RESIDENT_ID（身份证）； PASSPORT（护照）； HK_MACAO_PASS（港澳居民往来内地通行证）； TAIWAN_PASS（台湾居民来往大陆通行证）；agentTag 为1时 必传 |
| agent_cert_no         | 经办人证件号              | C        | String(32)  | agentTag 为1时 必传                                          |
| agent_file_name       | 经办签约授权委托书文件名  | C        | String(32)  | agentTag 为1时 必传                                          |
| agent_file_path       | 经办授权委托书文件路径    | C        | String(128) | agentTag 为1时 必传                                          |
| remark                | 备注说明                  | C        | String(128) | 备注说明                                                     |
| ret_url               | 电子合同签约结果回调通知  | C        | String(128) | 成功签约才通知                                               |