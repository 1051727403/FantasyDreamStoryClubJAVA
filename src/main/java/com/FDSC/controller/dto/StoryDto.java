package com.FDSC.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
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
