package com.FDSC.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryLatestDto {

    private Long storyId;

    private String storyName;

    private String deltaTime;

}
