package cn.cc.huifu.business.auth.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class HFAliApplyDto {

    private String huifuId;
    private AuthIdentityInfo authIdentityInfo;
    private ContactPersonInfo contactPersonInfo;

    @Data
    public static class AuthIdentityInfo{
        /**
         * 枚举定义：
         * 0 - 个人/小微
         * 1 - 政府机构
         * 2 - 国营企业
         * 3 - 私营企业
         * 4 - 外资企业
         * 5 - 个体工商户
         * 7 - 事业单位
         * 示例值：3；
         */
        private String businessType;
    }

    @Data
    public static class ContactPersonInfo{
        private String name;
        private String mobile;
        private String idCardNumber;
    }

    // toMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("huifu_id", huifuId);

        if(Objects.nonNull(authIdentityInfo)) {
            Map<String, Object> auth_identity_info = new HashMap<>();
            auth_identity_info.put("business_type", authIdentityInfo.businessType);
            map.put("auth_identity_info", auth_identity_info);
        }

        if(Objects.nonNull(contactPersonInfo)) {
            Map<String, Object> contact_person_info = new HashMap<>();
            contact_person_info.put("name", contactPersonInfo.name);
            contact_person_info.put("mobile", contactPersonInfo.mobile);
            contact_person_info.put("id_card_number", contactPersonInfo.idCardNumber);
            map.put("contact_person_info", contact_person_info);
        }

        return map;
    }
}
