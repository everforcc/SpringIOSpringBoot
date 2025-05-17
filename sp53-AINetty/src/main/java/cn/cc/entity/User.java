package cn.cc.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String account;
    private String name;
    private String password;
} 