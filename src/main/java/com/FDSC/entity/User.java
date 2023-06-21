package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "sys_user")
@ToString
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Alias("用户名")
    private String username;
    //忽略密码不展示给前端
    @Alias("密码")
    private String password;

    @Alias("昵称")
    private String nickname;

    @Alias("邮箱")
    private String email;

    @Alias("电话")
    private String phone;
/*地址    start*/
    @Alias("校区")
    private String campus;

    @Alias("片区")
    private String area;

    @Alias("楼幢号")
    private String building;

    @Alias("宿舍号")
    private String dormitory;
/*地址    end*/
    @TableField(value = "createTime")
    private LocalDateTime createTime;

    @Alias("头像")
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    @Alias("是否为管理员")
    @TableField(value = "isAdmin")
    private String isAdmin;
//    //传给前端忽略
//    @JsonIgnore
//    public String getPassword() {
//        return password;
//    }

    //后端注册时获取不忽略
    @JsonProperty
    public void setPassword(final String password) {
        this.password = password;
    }


}
