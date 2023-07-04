package com.FDSC.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlideShowAnnounceDto {

    private Long id;

    private String title;

    private String content;

    private Integer isActivity;

    private String coverUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean shown;
}
