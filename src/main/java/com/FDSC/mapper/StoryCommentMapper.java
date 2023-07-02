package com.FDSC.mapper;

import com.FDSC.entity.StoryComment;
import com.FDSC.mapper.dto.StoryMapperCommentDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StoryCommentMapper extends BaseMapper<StoryComment> {


    @Update("update story_comment set content=#{content} where id=#{id}")
    void updateContent(Long id, String content);
    @Select("SELECT sc.id as fromId,sc.user_id as user_id,nickname,avatar_url as avatarUrl,parent_id as toId,content,topic_id as topicId,sc.create_time as time " +
            "FROM story_comment as sc,user " +
            "WHERE sc.story_id=#{storyId} AND user.id=sc.user_id")
    List<StoryMapperCommentDto> getStoryComments(long storyId);
}
