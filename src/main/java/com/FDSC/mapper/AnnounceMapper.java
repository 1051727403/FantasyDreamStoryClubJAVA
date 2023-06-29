package com.FDSC.mapper;

import com.FDSC.controller.dto.SlideShowDto;
import com.FDSC.entity.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnnounceMapper extends BaseMapper<Announcement> {

    @Select("Select a.id, a.cover_url from slideshow as s, announcement as a where s.announcement_id=a.id")
    public List<SlideShowDto> getSlideShow();

}
