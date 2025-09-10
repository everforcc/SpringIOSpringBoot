package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 业务拓展信息 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizContentDTO {

    @NotBlank
    @Size(max = 2)
    @JsonProperty("term_num")
    private String termNum; // 终端数量

    @Size(max = 80)
    @JsonProperty("term_ver")
    private String termVer; // 终端版本（可选）

    @NotNull
    @JsonProperty("fees")
    private Set<FeeDTO> fees; // 费率集合

    @NotBlank
    @Size(max = 8)
    private String mcc; // 商户MCC编号

    @NotNull
    @JsonProperty("activity_id")
    private Long activityId; // 归属活动信息

    @JsonProperty("withdrawal_type")
    private String withdrawalType; // 提款类型（可选）
}


