package com.FDSC.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class FragmentInfoDto {
    private String storyName;
    private String fragName;
    private int totalLike;
    private int totalCollection;
    private int totalComment;
    private String content;

}
