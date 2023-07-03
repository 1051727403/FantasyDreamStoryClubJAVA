package com.FDSC.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnounceDto {

    private Long AnnounceId;

    private String title;

    private String content;

    private String coverUrl;

    private String createTime;

    private String updateTime;
}
