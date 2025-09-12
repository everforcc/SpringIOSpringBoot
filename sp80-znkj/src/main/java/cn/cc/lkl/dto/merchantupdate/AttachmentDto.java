package cn.cc.lkl.dto.merchantupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * V3TkbsOpenMerchantAddTermRequest
 * 附件信息DTO
 */
@Data
public class AttachmentDto {
    /**
     * 附件路径
     */
    @JsonProperty("img_path")
    private String imgPath;

    /**
     * 附件类型(详见附件类型表)
     */
    @JsonProperty("img_type")
    private String imgType;
}
