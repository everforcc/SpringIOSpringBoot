## 请求接口业务参数说明(req_data)

| 参数名称    | 参数说明          | 请求类型 | 是否必须 | 数据类型              | schema                   |
| :---------- | :---------------- | :------- | :------- | :-------------------- | :----------------------- |
| merchant_no | 外部商户编号      | body     | TRUE     | string                |                          |
| bz_pos      | 见附录-业务类型表 | body     | TRUE     | string                |                          |
| term_num    | 终端数量          | body     | TRUE     | Integer               |                          |
| shop_id     | 网点编号          | body     | TRUE     | Long                  | 获取商户信息接口中有返回 |
| fees        | 费率信息集合      | body     | FALSE    | List< FeeInfoDto>     | 详见FeeInfoDto对象       |
| attachments | 附件信息集合      | body     | FALSE    | List< AttachmentDto > | 详见AttachmentDto对象    |
| org_code    | 鉴权机构号        | Body     | TRUE     | String                |                          |

### FeeInfoDto对象

| 参数名称 | 参数说明                   | 请求类型 | 是否必须 | 数据类型 | schema |
| :------- | :------------------------- | :------- | :------- | :------- | :----- |
| fee      | 费率                       | body     | TRUE     | Double   |        |
| top_fee  | 费率封顶上限               | body     | TRUE     | Double   |        |
| fee_type | 费率类型（详见费率代码表） | body     | TRUE     | String   |        |

### AttachmentDto对象

| 参数名称 | 参数说明                 | 请求类型 | 是否必须 | 数据类型 | schema |
| :------- | :----------------------- | :------- | :------- | :------- | :----- |
| img_path | 附件路径                 | body     | FALSE    | String   |        |
| img_type | 附件类型(详见附件类型表) | body     | FALSE    | String   |        |