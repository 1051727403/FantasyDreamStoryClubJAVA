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

import java.sql.Wrapper;
import java.util.List;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TagService tagService;

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

    public Result addOrUpdateTag(Tag tag) {
        try{
            System.out.println(tag.getId());
            System.out.println(tag.getTagName());

            return Result.success(tagService.saveOrUpdate(tag));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }
}
