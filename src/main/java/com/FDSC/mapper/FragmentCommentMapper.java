package com.FDSC.mapper;

import com.FDSC.entity.FragmentComment;
import com.FDSC.entity.FragmentLikeCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FragmentCommentMapper extends BaseMapper<FragmentComment> {


    @Update("update fragment_comment set content=#{content} where id=#{id}")
    void updateConten(Long id, String content);
}
