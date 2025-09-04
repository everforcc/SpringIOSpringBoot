package cn.cc.lkl.dto.merchantencry;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 费率信息 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeDTO {

    @NotBlank
    @Size(max = 20)
    @JsonProperty("fee_code")
    private String feeCode; // 费率类型

    @NotNull
    @JsonProperty("fee_value")
    private Double feeValue; // 费率值 百分比

    @JsonProperty("top_fee")
    private Double topFee; // 封顶值（可选）
}


