package com.FDSC.service;


import com.FDSC.common.Result;
import com.FDSC.controller.dto.SlideRecommendDto;
import com.FDSC.controller.dto.StoryItemDto;
import com.FDSC.entity.Story;
import com.FDSC.entity.User;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class StoryService extends ServiceImpl<StoryMapper, Story> {

    @Autowired
    private StoryMapper storyMapper;

    public List<String> getstorytag(String storyid){   return storyMapper.gettag(storyid);}


    public Result collectestory(String storyid, String userid) {
        try{
            return Result.success(storyMapper.collected(storyid,userid));
        }catch (Exception e){
            return Result.error("500","已收藏");
        }
    }

    public Result recommend() {
        try{
            List<StoryItemDto> list = storyMapper.getAllStoryItem();
            for (StoryItemDto item : list) {
                item.setLink("/APP/StoryInfo/?storyid=" + item.getStoryId());
            }
            return Result.success(list);
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result activityRecommend() {
        try{
            List<Story> list = storyMapper.getAll();
            List<SlideRecommendDto> slidelist = new ArrayList<>(list.size());
            for (Story story : list) {
                SlideRecommendDto temp = new SlideRecommendDto();
                temp.setCoverUrl(story.getCoverUrl());
                temp.setLink("/APP/StoryInfo/?storyid=" + story.getId());
                slidelist.add(temp);
            }
            return Result.success(slidelist);
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }
}
