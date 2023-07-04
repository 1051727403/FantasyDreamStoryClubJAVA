package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private Long id;

    private String title;

    private String content;

    private String coverUrl;

    private Boolean shown;
}
