package com.FDSC.controller;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Story;
import com.FDSC.entity.User;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.service.StoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/getStoryInfo")
    public Result getStory(@RequestParam String storyid){
        return Result.success(storyService.getById(storyid));
    }

    @GetMapping("/getStoryTag")
    public Result getStoryTag(@RequestParam String storyid){
        return Result.success(storyService.getStoryTag(storyid));
    }
    @PostMapping("/collecteStory")
    public Result collecteStory(@RequestParam String storyid,
                                @RequestParam String userid){
      return storyService.collecteStory(storyid,userid);
    };

    @PostMapping("uncollectStory")
    public Result uncollectStory(@RequestParam String storyid,
                                 @RequestParam String userid){
        return storyService.uncollecteStory(storyid,userid);
    }

    @GetMapping("/recommendStory")
    public Result recommendStory() {
        return storyService.recommend();
    }

    @GetMapping("/slideShow")
    public Result slideShow() {
        return storyService.slideShow();
    }

    @GetMapping("/latestStory")
    public Result latestStory() { return storyService.latestStory(); }

    @GetMapping("/hotStory")
    public Result hotStory() { return storyService.hotStory(); }

    @GetMapping("/usersStories")
    public Result usersStories(@RequestParam String userid){return storyService.usersStories(userid);}

    @GetMapping("/usersStoriesWithFragment")
    public Result usersStoriesWithFragment(@RequestParam String userid){return storyService.StoriesWithFragment(userid);}

    @GetMapping("/usersCollectStories")
    public Result usersCollectStories(@RequestParam String userid){return storyService.usersCollectStories(userid);}

    @PostMapping("/saveStory")
    public Result saveStory(@RequestParam String userid,
                            @RequestParam String storyName,
                            @RequestParam String introduce,
                            @RequestParam String coverUrl){
        try{
            Story story = new Story();
            story.setStoryName(storyName);
            story.setUserId(Long.valueOf(userid));
            story.setIntroduce(introduce);
            story.setCoverUrl(coverUrl);
            storyService.save(story);
            return Result.success(story.getId());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"数据缺失");
        }

    }






}
