package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String userName;
    private String nickName;
    private String avatarUrl;
    private String password;
    private Integer removed;
}
