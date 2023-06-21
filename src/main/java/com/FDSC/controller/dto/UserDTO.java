package com.FDSC.controller.dto;

import lombok.Data;

/*
* 后端返回前端的参数
* */
@Data
public class UserDTO {
    String username;
//    String password;
    String nickname;
    String avatarUrl;
    String token;
}
