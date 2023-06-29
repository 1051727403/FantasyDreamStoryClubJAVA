package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

//@data 是lombok中的插件能够默认写set和get所有东西
@Data
@NoArgsConstructor//自动创建无参构造
@AllArgsConstructor//自动创建有参构造
//mybatis-plus会直接去数据库中查找，名字必须一致或者用tablename
@TableName(value = "user")
@ToString
public class User {
    @TableId(type = IdType.AUTO)
    private long id;

    @Alias("用户名")
    @TableField(value = "username")
    private String username;
    //忽略密码不展示给前端
    @Alias("密码")
    @TableField(value = "password")
    private String password;

    @Alias("昵称")
    @TableField(value = "nickname")
    private String nickname;

    @Alias("头像")
    @TableField(value = "avatar_url")
    private String avatarUrl;

    @Alias("总点赞量")
    @TableField(value = "total_like")
    private Integer totalLike;

    @Alias("是否为管理员")
    @TableField(value = "is_admin")
    private Integer isAdmin;

    @Alias("创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


    //后端注册时获取不忽略
    @JsonProperty
    public void setPassword(final String password) {
        this.password = password;
    }


}
