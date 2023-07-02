package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FragmentMapperCommentDto {
    private Long fromId;
    private Long userId;
    private String nickname;
    private String avatarUrl;
    private Long toId;
    private String content;
    private Long topicId;
    private Integer totalComment;
    private LocalDateTime time;
}
