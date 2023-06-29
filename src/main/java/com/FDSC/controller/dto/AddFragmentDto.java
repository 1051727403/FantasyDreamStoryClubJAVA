package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class AddFragmentDto {
    private Long userId;
    private Long storyId;
    private Long parentId;
    private String fragmentName;
    private String content;
    private Integer allowRelay;
}
