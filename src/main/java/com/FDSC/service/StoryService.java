package com.FDSC.service;


import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import java.time.Duration;
import com.FDSC.controller.dto.SlideShowDto;
import com.FDSC.controller.dto.StoryItemDto;
import com.FDSC.controller.dto.StoryLatestDto;
import com.FDSC.entity.Story;
import com.FDSC.mapper.AnnounceMapper;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.dto.StoryLatestTempDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class StoryService extends ServiceImpl<StoryMapper, Story> {

    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private AnnounceMapper announceMapper;

    public List<String> getStoryTag(String storyid){   return storyMapper.gettag(storyid);}

    public void setStoryTag(Long storyid,List<Integer> tags){
        storyMapper.settag(storyid,tags);
    }


    public Result collecteStory(String storyid, String userid) {
        try{
            return Result.success(storyMapper.collected(storyid,userid));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"已收藏");
        }
    }

    public Result uncollecteStory(String storyid, String userid) {
        try{
            return Result.success(storyMapper.uncollected(storyid,userid));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"取消收藏失败");
        }
    }

    public Result recommend() {
        try{
            List<StoryItemDto> list =  storyMapper.getAllStoryItem();
            Collections.shuffle(list);
            return Result.success(list.subList(0, Math.min(list.size(), 15)));
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result slideShow() {
        try{
            return Result.success(announceMapper.getSlideShow());
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    private String deltaTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return "刚刚";
        }

        long minutes = duration.toMinutes();
        if (minutes < 60) {
            return minutes + "分钟前";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + "小时前";
        }

        long days = duration.toDays();
        if (days < 30) {
            return days + "天前";
        }

        long months = days / 30;
        if (months < 12) {
            return months + "个月前";
        }

        long years = months / 12;
        return years + "年前";
    }

    public Result latestStory() {
        try{
            List<StoryLatestTempDto> list = storyMapper.getLatestStory();
            List<StoryLatestDto> latests = new ArrayList<>(list.size());
            for (StoryLatestTempDto story : list) {
                StoryLatestDto temp = new StoryLatestDto();
                temp.setStoryId(story.getStoryId());
                temp.setStoryName(story.getStoryName());
                temp.setDeltaTime(deltaTime(story.getUpdateTime()));
                latests.add(temp);
            }
            return Result.success(latests.subList(0, Math.min(latests.size(), 20)));
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result hotStory() {
        try{
            List<StoryItemDto> list = storyMapper.getHotStory();
            return Result.success(list.subList(0, Math.min(list.size(), 7)));
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    public Result usersStories(String userid) {
        try{
            return Result.success(storyMapper.usersStories(userid));
        }
        catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result usersCollectStories(String userid) {
        try{
            return Result.success(storyMapper.collectedStories(userid));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"已收藏");
        }
    }

    public Result StoriesWithFragment(String userid) {

            try{
                return Result.success(storyMapper.StoriessWithFragment(userid));
            }
        catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败4");
        }
    }

    public Result checkCollect(String userId, String storyId) {
        try{
            return Result.success(storyMapper.checkCollect(userId,storyId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"数据错误");
        }
    }
}
