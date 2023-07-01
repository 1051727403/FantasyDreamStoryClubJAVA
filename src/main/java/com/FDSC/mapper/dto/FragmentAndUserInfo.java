package com.FDSC.mapper.dto;

import lombok.Data;

@Data
public class FragmentAndUserInfo {
    private Long id;
    private Long storyId;
    private Long parentId;
    private String fragmentName;
    private String content;
    private Integer allowRelay;
    private Integer totalLike;
    private Integer totalCollection;
    private Integer totalComment;
    //author
    private Long userId;
    private String nickname;
    private String avatarUrl;
    private Integer authorTotalLike;




}
