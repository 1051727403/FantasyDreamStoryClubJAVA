package com.FDSC.mapper;

import com.FDSC.controller.dto.SlideShowDto;
import com.FDSC.entity.Announcement;
import com.FDSC.mapper.dto.AnnounceShowTempDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnnounceMapper extends BaseMapper<Announcement> {

    @Select("Select a.id, a.cover_url from slideshow as s, announcement as a where s.announcement_id=a.id")
    public List<SlideShowDto> getSlideShow();

    @Select("Select id, title, update_time from announcement WHERE is_activity = 0 ORDER BY update_time DESC")
    public List<AnnounceShowTempDto> getAllAnnounce();

    @Select("Select id, title, update_time \n" +
            "from announcement \n" +
            "WHERE is_activity = 0 \n"+
            "ORDER BY update_time DESC\n" +
            "LIMIT 2")
    public List<AnnounceShowTempDto> getTwoAnnounce();

    @Select("Select * FROM announcement WHERE is_activity = 0 and id = #{id}")
    public Announcement getAnnounce(Integer id);

}
