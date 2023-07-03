package com.FDSC.mapper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnounceShowTempDto {
    private Long id;

    private String title;

    private LocalDateTime createTime;
}
