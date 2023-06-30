package com.FDSC.mapper;

import com.FDSC.controller.dto.FragmentInfoDto;
import com.FDSC.controller.dto.FragmentDto;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.Story;
import com.FDSC.mapper.dto.FragmentMapperCommentDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FragmentMapper extends BaseMapper<Fragment> {


    @Select("Select story_name as storyName,fragment.fragment_name as fragName," +
            "fragment.total_like as totalLike,fragment.total_collection as totalCollection," +
            "fragment.total_comment as totalComment,left(content,20) as content " +
            " from fragment,story " +
            " where fragment.story_id=story.id " +
            " and fragment.user_id=#{userid}")
    List<FragmentInfoDto> usersfrag(String userid);

    @Select("SELECT user.id as id,user.nickname,user.avatar_url AS avatarUrl,user.total_like as totalLike FROM fragment,user WHERE fragment.user_id=user.id and fragment.id=#{fragmentId};")
    FragmentDto.AuthorDTO getFragmentAuthor(long fragmentId);


    @Select("SELECT fc.id as fromId,nickname,avatar_url as avatarUrl,parent_id as toId,content,topic_id as topicId,fc.create_time as time " +
                   "FROM fragment_comment as fc,user " +
                   "WHERE fc.fragment_id=#{fragmentId} AND user.id=fc.user_id")
    List<FragmentMapperCommentDto> getFragmentComments(long fragmentId);
}