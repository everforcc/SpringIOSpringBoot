package com.cc.sp04security.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUser implements UserDetails {

    private int id;

    private String username;

    private String password;

    /**
     * 随后做单独把 role做个表，然后用id对应， Long[] roles
     * 然后使用的时候再查出来
     */
    private List<String> roles;

    public String token(){
        // 模拟生成token
        return this.id + "-" + this.id;
    }


    /**
     * 正式使用改为加密数据，学习时方便测试
     * @param json
     * @return
     */
    public static CustomUser tokenToUser(String json){
        log.info("json: " + json);
        return JSONObject.parseObject(json,CustomUser.class);
    }

    public CustomUser(int id) {
        this.id = id;
    }

    /**
     * 构造返回用户的权限
     * 由于springsecurity，必须以 ROLE_ 开头
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        roles = new ArrayList<>();
        roles.add(UserRole.ROLE_ROOT);
        roles.add(UserRole.ROLE_ADMIN);
        roles.add(UserRole.ROLE_TEST);
        roles.add(UserRole.ROLE_GUEST);

        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        //return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }



}

