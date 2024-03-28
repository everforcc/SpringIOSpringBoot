package com.cc.sp04security.web;

import com.cc.sp04security.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service("permission")
public class PermissionService {

    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = customUser.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        log.info("当前用户权限{}，需要的权限{}", roles.toString(), permission);
        return roles.contains(permission);
    }

}
