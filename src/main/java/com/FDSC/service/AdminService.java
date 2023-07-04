package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.AnnounceDto;
import com.FDSC.controller.dto.SlideShowAnnounceDto;
import com.FDSC.entity.Announcement;
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

    public Result deleteAnnounce(Integer id) {
        try{
            return Result.success(adminMapper.deleteAnnounce(id));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"删除失败");
        }
    }

    public Result deleteBatchAnnounce(List<Integer> ids) {
        try{
            return Result.success(adminMapper.deleteBatchAnnounce(ids));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"删除失败");
        }
    }

    public Result upAnnounceInfo(Announcement a) {
        if(a.getId() == null ) {
            try {
                return Result.success(adminMapper.saveAnnounce(a.getTitle(), a.getCoverUrl(), a.getContent(), a.getIsActivity()));
            } catch (Exception e) {
                return Result.error(Constants.CODE_500, "新增公告失败");
            }
        }
        try {
            return Result.success(adminMapper.updateAnnounce(a.getId(), a.getTitle(), a.getCoverUrl(), a.getContent()));
        }
        catch (Exception e){
            return Result.error(Constants.CODE_500, "更新失败");
        }
    }

    public Result upActivityInfo(SlideShowAnnounceDto a) {
        System.out.println(a);
        try {
            Announcement temp = new Announcement();
            temp.setId(a.getId());
            temp.setContent(a.getContent());
            temp.setIsActivity(a.getIsActivity());
            temp.setTitle(a.getTitle());
            temp.setCoverUrl(a.getCoverUrl());
            temp.setCreateTime(a.getCreateTime());
            temp.setUpdateTime(a.getUpdateTime());
            upAnnounceInfo(temp);
            Long id = a.getId();
            if (a.getId() == null) {
                id = adminMapper.lastInsertAnnounceId();
                if (a.getShown()) {
                    adminMapper.saveSlideShow(id);
                    return Result.success();
                }
            } else {
                if (a.getShown()) {
                    adminMapper.saveSlideShow(id);
                    return Result.success();
                }
                adminMapper.deletSlideShow(id);
                return Result.success();
            }
            return Result.success();
        }
        catch (Exception e) {
            return Result.error(Constants.CODE_500, "更新失败");
        }
    }
}
