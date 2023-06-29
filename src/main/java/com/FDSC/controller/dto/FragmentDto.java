package com.FDSC.controller.dto;


import lombok.Data;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class FragmentDto {
    private String id;
    private long rootId;
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
        private long id;
        private String headImg;
        private String comment;
        private LocalDateTime time;
        private boolean inputShow;
        private List<ReplyDTO> reply;


        @Data
        public static class ReplyDTO {
            private String from;
            private long fromId;
            private String fromHeadImg;
            private String to;
            private long toId;
            private String comment;
            private LocalDateTime time;
            private boolean inputShow;

        }
    }

    @Data
    public static class ChildDto{
        private long id;
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
