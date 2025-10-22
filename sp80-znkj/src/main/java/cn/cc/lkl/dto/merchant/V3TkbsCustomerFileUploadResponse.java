package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商户进件文件上传
 * https://o.lakala.com/p/#/document/detail?id=1143
 */
@Data
public class V3TkbsCustomerFileUploadResponse {

    /**
     * 批量号
     */
    @JsonProperty("batch_no")
    private String batchNo;

    /**
     * 状态	00 成功， 01 正在处理中， 02 失败
     */
    @JsonProperty("status")
    private String status;

    /**
     * 文件上传成功后的url
     */
    @JsonProperty("url")
    private String url;

    @JsonProperty("show_url")
    private String showUrl;

    /**
     * ocr结果
     */
    @JsonProperty("result")
    private String result;

}
