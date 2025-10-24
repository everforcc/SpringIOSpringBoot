package cn.cc.lkl.dto.merchant;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * https://o.lakala.com/p/#/document/detail?id=1052
 * 获取地区信息
 */
@Data
public class V3TkbsOrganizationParentCodeRequest extends LKLBaseRequest {

    /**
     * 父地区编码
     * 如果1 查询所有
     */
    @Size(max = 32)
    @JsonProperty("parent_code")
    private String parentCode;

    /**
     * 机构编号
     * 鉴权机构
     */
    @Size(max = 32)
    @JsonProperty("org_code")
    private String orgCode;

    @Override
    public FunctionCodeEnum gFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_ORGANIZATION_PARENT_CODE;
    }
}
