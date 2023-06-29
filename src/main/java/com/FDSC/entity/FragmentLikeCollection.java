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
@TableName(value = "fragment_like_collection")
public class FragmentLikeCollection {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Alias("用户id")
    @TableField(value = "user_id")
    private Long userId;

    @Alias("片段id")
    @TableField(value = "fragment_id")
    private Long fragmentId;

    @Alias("是否表示点赞")
    @TableField(value = "is_like")
    private Integer isLike;

    @Alias("是否表示收藏")
    @TableField(value = "is_collection")
    private Integer isCollection;

    @Alias("创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Alias("更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
