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
@TableName(value = "announcement")
public class Announcement {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("标题")
    @TableField(value = "title")
    private String title;

    @Alias("公告内容")
    @TableField(value = "content")
    private String content;

    @Alias("是否是活动")
    @TableField(value = "is_activity")
    private Integer isActivity;

    @Alias("封面")
    @TableField(value = "cover_url")
    private String coverUrl;

    @Alias("创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
