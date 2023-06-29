package com.FDSC.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryItemDto {

    @TableField(value = "id")
    private Long storyId;

    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "total_like")
    private Integer totalLike;
    @TableField(value = "total_collection")
    private Integer totalCollection;
    @TableField(value = "total_comment")
    private Integer totalComment;

    @TableField(value = "story_name")
    private String storyName;

    @TableField(value = "cover_url")
    private String coverUrl;
}
