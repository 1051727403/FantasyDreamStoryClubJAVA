package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "story")
public class Story {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Alias("总点赞数（冗余）")
    @TableField(value = "total_like")
    private Integer totalLike;

    @Alias("总收藏数（冗余）")
    @TableField(value = "total_collection")
    private Integer totalCollection;

    @Alias("总评论数（冗余）")
    @TableField(value = "total_comment")
    private Integer totalComment;

    @Alias("故事名称")
    @TableField(value = "story_name")
    private String storyName;

    @Alias("简介")
    @TableField(value = "introduce")
    private String introduce;

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