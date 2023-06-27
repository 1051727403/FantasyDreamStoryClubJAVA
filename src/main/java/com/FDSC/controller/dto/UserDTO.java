package com.FDSC.controller.dto;

import lombok.Data;

/*
* 后端返回前端的参数
* */
@Data
public class UserDTO {
    long id;
    String username;
    String nickname;
    String avatarUrl;
    Integer totalLike;
    String token;
}
