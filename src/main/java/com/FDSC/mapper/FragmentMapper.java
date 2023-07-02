package com.FDSC.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.FDSC.controller.dto.FragmentDto;
import com.FDSC.controller.dto.FragmentInfoDto;
import com.FDSC.entity.Fragment;
import com.FDSC.mapper.dto.FragmentAndUserInfo;
import com.FDSC.mapper.dto.FragmentMapperCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FragmentMapper extends BaseMapper<Fragment> {


    @Select("Select story_name as storyName,fragment.fragment_name as fragName," +
            "fragment.total_like as totalLike,fragment.total_collection as totalCollection," +
            "fragment.total_comment as totalComment,left(content,20) as content," +
            " story.id as storyId,fragment.id as fragmentId" +
            " from fragment,story " +
            " where fragment.story_id=story.id " +
            " and fragment.user_id=#{userid}")
    List<FragmentInfoDto> usersfrag(String userid);

    @Select("SELECT user.id as id,user.nickname,user.avatar_url AS avatarUrl,user.total_like as totalLike FROM fragment,user WHERE fragment.user_id=user.id and fragment.id=#{fragmentId};")
    FragmentDto.AuthorDTO getFragmentAuthor(long fragmentId);


    @Select("SELECT fc.id as fromId,fc.user_id as user_id,nickname,avatar_url as avatarUrl,parent_id as toId,content,topic_id as topicId,fc.create_time as time " +
                   "FROM fragment_comment as fc,user " +
                   "WHERE fc.fragment_id=#{fragmentId} AND user.id=fc.user_id")
    List<FragmentMapperCommentDto> getFragmentComments(long fragmentId);

    @Select("select * from fragment where parent_id=#{id}")
    List<Fragment> getchildren(Long id);


    @Select("SELECT\n" +
            "    fragment.id,\n" +
            "    fragment.story_id,\n" +
            "    fragment.parent_id,\n" +
            "    fragment.fragment_name,\n" +
            "    fragment.content,\n" +
            "    fragment.allow_relay,\n" +
            "    fragment.total_like,\n" +
            "    fragment.total_collection,\n" +
            "    fragment.total_comment,\n" +
            "    user.id AS user_id,\n" +
            "    user.nickname,\n" +
            "    user.avatar_url,\n" +
            "    user.total_like AS author_total_like\n" +
            "FROM\n" +
            "    fragment\n" +
            "JOIN\n" +
            "    user ON fragment.user_id = user.id\n" +
            "WHERE\n" +
            "    fragment.story_id = #{storyId};")
    List<FragmentAndUserInfo> selectByStoryId(long storyId);
}