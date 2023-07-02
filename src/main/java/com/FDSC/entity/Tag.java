package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("标签名")
    @TableField(value = "tag_name")
    private String tagName;

    @Alias("创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
