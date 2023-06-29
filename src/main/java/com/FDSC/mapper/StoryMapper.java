package com.FDSC.mapper;

import com.FDSC.controller.dto.SlideRecommendDto;
import com.FDSC.controller.dto.StoryItemDto;
import com.FDSC.entity.Story;
import com.FDSC.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface StoryMapper extends BaseMapper<Story> {

    @Select("Select * from story")
    public List<Story> getAll();

    @Select("Select id as story_id, user_id, total_like, total_collection, total_comment, story_name, cover_url from story")
    public List<StoryItemDto> getAllStoryItem();

    @Select("Select tag_name from tag,story_tag where tag.id=tag_id and story_id=#{storyid}")
    public List<String> gettag(String storyid);


    @Insert("Insert into story_collection(user_id,story_id) values(#{userid},#{storyid})")
    public boolean collected(String storyid, String userid);

    @Select("Select id as storyId,total_like,total_collection,total_comment,story_name,cover_url from story where user_id=#{userid}")
    List<StoryItemDto> usersStories(String userid);

    @Select("Select story.id as storyId,total_like,total_collection,total_comment,story_name,cover_url " +
            "from story,story_collection " +
            "where story.id=story_id " +
            "and story_collection.user_id=#{userid}")
    List<StoryItemDto>  collectedStories(String userid);
}
