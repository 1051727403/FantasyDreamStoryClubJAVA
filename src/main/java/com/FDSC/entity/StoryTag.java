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
@TableName(value = "story_tag")
public class StoryTag {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("故事id")
    @TableField(value = "story_id")
    private Long storyId;

    @Alias("标签id")
    @TableField(value = "tag_id")
    private Long tagId;

    @Alias("创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
