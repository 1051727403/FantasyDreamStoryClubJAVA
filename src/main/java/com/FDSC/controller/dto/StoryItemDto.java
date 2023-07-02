package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class StoryItemDto {

    private Long storyId;

    private Long userId;

    private Integer totalLike;

    private Integer totalCollection;

    private Integer totalComment;

    private String storyName;

    private String coverUrl;
}
