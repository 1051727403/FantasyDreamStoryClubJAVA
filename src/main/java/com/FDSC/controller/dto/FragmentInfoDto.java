package com.FDSC.controller.dto;

import lombok.Data;

@Data
public class FragmentInfoDto {


    private Integer storyId;

    private Integer fragmentId;
    private String storyName;
    private String fragName;
    private int totalLike;
    private int totalCollection;
    private int totalComment;
    private String content;

}
