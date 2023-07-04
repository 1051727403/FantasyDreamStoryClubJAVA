package com.FDSC.mapper;

import com.FDSC.entity.Announcement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Update("Update user " +
            "set removed = 1 " +
            "where id=#{userId}")
    boolean deleteUser(String userId);

    boolean deleteBatchUser(List<Integer> userId);


    @Delete("delete from tag " +
            "where id=#{tagId}")
    boolean deleteTag(String tagId);

    boolean deleteBatchTag(List<Integer> tagIds);
    @Delete("delete from story " +
            "where id=#{storyId}")
    boolean deleteStory(String storyId);

    @Delete("delete from announcement " +
            "where id=#{id}")
    boolean deleteAnnounce(Integer id);

    boolean deleteBatchAnnounce(List<Integer> ids);

    @Update("UPDATE announcement\n" +
            "SET title = #{title}, cover_url = #{coverUrl}, content = #{content}\n" +
            "WHERE id = #{id}")
    boolean updateAnnounce(Long id, String title, String coverUrl, String content);

    @Insert("INSERT INTO announcement (title, cover_url, content, is_activity) \n" +
            "VALUES (#{title}, #{coverUrl}, #{content}, #{isActivity})")
    boolean saveAnnounce(String title, String coverUrl, String content, Integer isActivity);


    @Insert("INSERT INTO slideshow (announcement_id, type) \n" +
            "VALUES (#{announcement_id}, 0)")
    boolean saveSlideShow(Long announcement_id);

    @Delete("delete from slideshow " +
            "where announcement_id=#{announcement_id}")
    boolean deletSlideShow(Long announcement_id);

    @Select("SELECT LAST_INSERT_ID()")
    Long lastInsertAnnounceId();
}
