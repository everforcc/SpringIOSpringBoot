-- 依据 docs/lkl/接口文档/新增商户进件.md 生成
-- 主表：商户进件
CREATE TABLE IF NOT EXISTS tkbs_merchant_encry_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  org_code BIGINT NOT NULL COMMENT '机构编号',
  user_no BIGINT NOT NULL COMMENT '商户归属用户信息',
  email VARCHAR(80) NOT NULL COMMENT '商户邮箱',
  busi_code VARCHAR(20) NOT NULL COMMENT '业务类型',
  mer_reg_name VARCHAR(80) NOT NULL COMMENT '商户注册名称',
  mer_type VARCHAR(80) NOT NULL COMMENT '商户注册类型',
  mer_name VARCHAR(80) NOT NULL COMMENT '商户名称(经营名称)',
  mer_addr VARCHAR(80) NOT NULL COMMENT '商户详细地址',
  province_code VARCHAR(16) NOT NULL COMMENT '省代码',
  city_code VARCHAR(16) NOT NULL COMMENT '市代码',
  county_code VARCHAR(16) NOT NULL COMMENT '区县代码',
  license_name VARCHAR(80) COMMENT '营业执照名称（可选）',
  license_no VARCHAR(40) COMMENT '营业执照号码（可选）',
  license_dt_start CHAR(10) COMMENT '营业执照开始时间（可选）',
  license_dt_end CHAR(10) COMMENT '营业执照过期时间（可选）',
  latitude VARCHAR(20) NOT NULL COMMENT '经度',
  longtude VARCHAR(20) NOT NULL COMMENT '纬度（字段名按文档）',
  source VARCHAR(20) NOT NULL COMMENT '进件来源 APP/H5',
  business_content VARCHAR(64) NOT NULL COMMENT '商户经营内容',
  is_legal_person TINYINT(1) COMMENT '是否法人进件（可选，不传默认法人）',
  lar_name VARCHAR(20) NOT NULL COMMENT '法人姓名',
  lar_id_type VARCHAR(8) NOT NULL COMMENT '法人证件类型',
  lar_id_card VARCHAR(40) NOT NULL COMMENT '法人证件号码',
  lar_id_card_start CHAR(10) NOT NULL COMMENT '法人证件开始日期',
  lar_id_card_end CHAR(10) NOT NULL COMMENT '法人证件过期时间',
  contact_mobile VARCHAR(20) NOT NULL COMMENT '商户联系人手机号',
  contact_name VARCHAR(32) NOT NULL COMMENT '商户联系人姓名',
  openning_bank_code VARCHAR(20) NOT NULL COMMENT '结算账户开户行号',
  openning_bank_name VARCHAR(40) NOT NULL COMMENT '结算账户开户行名称',
  clearing_bank_code VARCHAR(20) NOT NULL COMMENT '结算账户清算行号',
  settle_province_code VARCHAR(20) NOT NULL COMMENT '结算信息省份代码',
  settle_province_name VARCHAR(20) NOT NULL COMMENT '结算信息省份名称',
  settle_city_code VARCHAR(20) NOT NULL COMMENT '结算信息城市代码',
  settle_city_name VARCHAR(20) NOT NULL COMMENT '结算信息城市名称',
  account_no VARCHAR(40) NOT NULL COMMENT '结算人银行卡号',
  account_name VARCHAR(40) NOT NULL COMMENT '结算人账户名称',
  account_type VARCHAR(8) NOT NULL COMMENT '结算账户类型',
  account_id_type VARCHAR(8) COMMENT '结算人证件类型（可选，空同法人）',
  account_id_card VARCHAR(8) NOT NULL COMMENT '结算人证件号码',
  account_id_dt_start VARCHAR(8) COMMENT '结算人证件开始时间（可选，空同法人）',
  account_id_dt_end VARCHAR(8) COMMENT '结算人证件过期时间（可选，空同法人）',
  external_no VARCHAR(32) COMMENT '外部编号（可选）',
  settle_type VARCHAR(10) NOT NULL COMMENT '结算类型',
  shop_id VARCHAR(20) COMMENT '网点代码（可选）',
  settlement_type VARCHAR(20) NOT NULL COMMENT '结算方式',
  regular_settlement_time INT COMMENT '定时结算时间（可选）',
  contract_no VARCHAR(80) COMMENT '电子合同编号（可选）'
) COMMENT='商户进件主表';

-- 子表：业务拓展信息（1:1）
CREATE TABLE IF NOT EXISTS tkbs_biz_content (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  request_id BIGINT NOT NULL COMMENT '关联主表ID',
  term_num VARCHAR(2) NOT NULL COMMENT '终端数量',
  term_ver VARCHAR(80) COMMENT '终端版本（可选）',
  mcc VARCHAR(8) NOT NULL COMMENT '商户MCC编号',
  activity_id BIGINT NOT NULL COMMENT '归属活动信息',
  withdrawal_type VARCHAR(32) COMMENT '提款类型（可选）',
  CONSTRAINT fk_biz_request FOREIGN KEY (request_id) REFERENCES tkbs_merchant_encry_request(id)
) COMMENT='业务拓展信息表';

-- 子表：费率信息（1:N）
CREATE TABLE IF NOT EXISTS tkbs_biz_fee (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  biz_id BIGINT NOT NULL COMMENT '关联业务拓展信息表ID',
  fee_code VARCHAR(20) NOT NULL COMMENT '费率类型',
  fee_value DOUBLE NOT NULL COMMENT '费率值 百分比',
  top_fee DOUBLE COMMENT '封顶值（可选）',
  CONSTRAINT fk_fee_biz FOREIGN KEY (biz_id) REFERENCES tkbs_biz_content(id)
) COMMENT='费率信息表';

-- 子表：附件信息（1:N）
CREATE TABLE IF NOT EXISTS tkbs_attachment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  request_id BIGINT NOT NULL COMMENT '关联主表ID',
  file_id VARCHAR(20) NOT NULL COMMENT '文件地址 URL',
  file_type VARCHAR(80) NOT NULL COMMENT '附件类型',
  CONSTRAINT fk_att_request FOREIGN KEY (request_id) REFERENCES tkbs_merchant_encry_request(id)
) COMMENT='附件信息表';