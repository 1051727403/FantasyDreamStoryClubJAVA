package com.FDSC.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.FDSC.entity.FragmentComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FragmentCommentMapper extends BaseMapper<FragmentComment> {


    @Update("update fragment_comment set content=#{content} where id=#{id}")
    void updateContent(Long id, String content);
}
