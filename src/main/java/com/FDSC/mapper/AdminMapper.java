package com.FDSC.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

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
}
