package com.FDSC.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoryNewDto {
    private Long id;
    private Long storyId;

    private Long userId;

    private String  storyName;

    private String  introduce;

    private String coverUrl;

    private List<Long> tags;
}
