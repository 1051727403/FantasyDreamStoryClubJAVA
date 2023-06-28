package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FragmentMapperCommentDto {
    private Long fromId;
    private String nickname;
    private String avatarUrl;
    private Long toId;
    private String content;
    private Long topicId;
    private LocalDateTime time;
}
