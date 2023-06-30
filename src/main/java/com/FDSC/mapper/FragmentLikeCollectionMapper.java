package com.FDSC.mapper;

import com.FDSC.entity.Fragment;
import com.FDSC.entity.FragmentLikeCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FragmentLikeCollectionMapper extends BaseMapper<FragmentLikeCollection> {


    @Update("update fragment_like_collection set is_like=#{beLike} where user_id=#{userId} and fragment_id=#{fragmentId}")
    void changeLike(Long userId, Long fragmentId, Integer beLike);

    @Insert("insert into fragment_like_collection(user_id,fragment_id,is_like,is_collection) values (#{userId},#{fragmentId},#{beLike},0)")
    void insertone(Long userId, Long fragmentId, Integer beLike);
    @Update("update fragment_like_collection set is_collection=#{beCollection} where user_id=#{userId} and fragment_id=#{fragmentId}")
    void changeCollection(Long userId, Long fragmentId, Integer beCollection);
}
