package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoryTempDto {
    private Long id;

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
