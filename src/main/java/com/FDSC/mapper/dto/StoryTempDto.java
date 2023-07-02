package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryTempDto {
    private Long storyId;

    private Long userId;

    private Integer totalLike;

    private Integer totalCollection;

    private Integer totalComment;

    private String storyName;

    private String introduce;

    private String coverUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long tagId;

    private String tagName;
}
