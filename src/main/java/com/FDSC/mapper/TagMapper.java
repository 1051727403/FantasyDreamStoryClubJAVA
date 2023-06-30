package com.FDSC.mapper;

import com.FDSC.controller.dto.TagDto;
import com.FDSC.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag>  {
    @Select("Select id as tag_id, tag_name from tag")
    public List<TagDto> getAll();

}
