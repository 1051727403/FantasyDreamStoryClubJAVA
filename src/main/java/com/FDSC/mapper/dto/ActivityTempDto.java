package com.FDSC.mapper.dto;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityTempDto {
    private Long id;

    private String title;

    private String content;

    private Integer isActivity;

    private String coverUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long tempId;
}
