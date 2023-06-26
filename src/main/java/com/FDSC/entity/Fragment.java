package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
@TableName(value = "fragment")
public class Fragment {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Alias("故事id")
    @TableField(value = "story_id")
    private Long storyId;

    @Alias("父节点id")
    @TableField(value = "parent_id")
    private Long parentId;

    @Alias("片段名")
    @TableField(value = "fragment_name")
    private String fragmentName;

    @Alias("片段内容")
    @TableField(value = "content")
    private String content;

    @Alias("是否允许接龙")
    @TableField(value = "allow_relay")
    private Integer allowRelay;

    @Alias("总点赞数（冗余）")
    @TableField(value = "total_like")
    private Integer totalLike;

    @Alias("总收藏数（冗余）")
    @TableField(value = "total_collection")
    private Integer totalCollection;

    @Alias("总评论数（冗余）")
    @TableField(value = "total_comment")
    private Integer totalComment;

    @Alias("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
