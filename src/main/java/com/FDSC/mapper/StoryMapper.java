package com.FDSC.mapper;

import com.FDSC.entity.Story;
import com.FDSC.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface StoryMapper extends BaseMapper<Story> {

    @Select("Select tag_name from tag,story_tag where tag.id=tag_id and story_id=#{storyid}")
    public List<String> gettag(String storyid);


}
