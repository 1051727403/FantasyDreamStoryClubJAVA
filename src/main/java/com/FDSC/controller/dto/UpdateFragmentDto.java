package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class UpdateFragmentDto {
    private Long fragmentId;
    private String fragmentName;
    private String content;
    private Integer allowRelay;
}
