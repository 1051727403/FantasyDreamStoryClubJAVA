package com.FDSC.mapper;

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


    @Update("delete from tag " +
            "where id=#{tagId}")
    boolean deleteTag(String tagId);

    boolean deleteBatchTag(List<Integer> tagIds);

}
