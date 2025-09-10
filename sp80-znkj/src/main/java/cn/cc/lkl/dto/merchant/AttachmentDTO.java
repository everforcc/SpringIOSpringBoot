package cn.cc.lkl.dto.merchant;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件信息 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {

    @NotBlank
    @Size(max = 20)
    private String id; // 文件地址 URL

    @NotBlank
    @Size(max = 80)
    private String type; // 附件类型
}


