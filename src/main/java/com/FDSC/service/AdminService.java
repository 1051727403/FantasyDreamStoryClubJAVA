package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Tag;
import com.FDSC.entity.User;
import com.FDSC.mapper.AdminMapper;
import com.FDSC.mapper.TagMapper;
import com.FDSC.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Result deleteUser(String userId) {
        try{
            return Result.success(adminMapper.deleteUser(userId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    public Result deleteBatchUser(List<Integer> userId) {
        try{
            return Result.success(adminMapper.deleteBatchUser(userId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    public Result deleteTag(String tagId) {
        try{
            return Result.success(adminMapper.deleteTag(tagId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    public Result deleteBatchTag(List<Integer> tagIds) {
        try{
            return Result.success(adminMapper.deleteBatchTag(tagIds));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    public Result deleteStory(String storyId) {
        try{
            return Result.success(adminMapper.deleteStory(storyId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }
}
