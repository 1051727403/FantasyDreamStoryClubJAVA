package com.FDSC.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoryDto {

    private Long storyId;

    private Long userId;

    private Integer totalLike;

    private Integer totalCollection;

    private Integer totalComment;

    private String storyName;

    private String introduce;

    private String coverUrl;

    private List<TagDto> tags;

    private String createTime;

    private String updateTime;

}
