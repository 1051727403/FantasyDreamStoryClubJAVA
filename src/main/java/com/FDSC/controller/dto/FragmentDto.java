package com.FDSC.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class FragmentDto {
    private String id;
    private Long rootId;
    private String topic;
    private AuthorDTO authorInfo;
    private int allowRelay;
    private int isLike;
    private int isCollected;
    private int totalLike;
    private int totalCollection;
    private int totalComment;
    private List<CommentDTO> comments;
    private String content;
    private List<ChildDto> children;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorDTO {
        private Long id;
        private String nickname;
        private String avatarUrl;
        private int totalLike;

    }

    //评论区点击时再读取
    @Data
    public static class CommentDTO {
        private String name;
        private Long id;
        private Long userId;
        private String headImg;
        private String comment;
        private LocalDateTime time;
        private Long topicId;
        private boolean inputShow;
        private List<ReplyDTO> reply;


        @Data
        public static class ReplyDTO {
            private String from;
            private Long fromId;
            private Long userId;
            private String fromHeadImg;
            private String to;
            private Long toId;
            private String comment;
            private LocalDateTime time;
            private boolean inputShow;

        }
    }

    @Data
    public static class ChildDto{
        private Long id;
        private String topic;
        private AuthorDTO authorInfo;
        private int allowRelay;
        private int isLike;
        private int isCollected;
        private int totalLike;
        private int totalCollection;
        private int totalComment;
        private List<CommentDTO> comments;
        private String content;
        private List<ChildDto> children;
    }

}
