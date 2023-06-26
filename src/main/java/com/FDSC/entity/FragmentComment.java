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
@TableName(value = "fragment_comment")
public class FragmentComment {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Alias("片段id")
    @TableField(value = "fragment_id")
    private Long fragmentId;

    @Alias("父节点id")
    @TableField(value = "parent_id")
    private Long parentId;

    @Alias("评论内容")
    @TableField(value = "content")
    private String content;

    @Alias("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
