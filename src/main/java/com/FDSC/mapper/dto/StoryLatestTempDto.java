package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryLatestTempDto {

    private Long storyId;

    private String storyName;

    private LocalDateTime updateTime;

}
