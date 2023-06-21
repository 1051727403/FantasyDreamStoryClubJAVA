package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    Integer id;
    String username;
    String nickname;
    String phone;
    String email;
    String campus;
    String area;
    String building;
    String dormitory;
}
