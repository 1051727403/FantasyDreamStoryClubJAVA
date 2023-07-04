package com.FDSC.controller;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.StoryNewDto;
import com.FDSC.entity.Story;
import com.FDSC.entity.StoryTag;
import com.FDSC.service.StoryService;
import com.FDSC.service.StoryTagService;
import com.FDSC.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private StoryTagService storyTagService;


    @GetMapping("/getStoryInfo")
    public Result getStory(@RequestParam String storyid){
        return Result.success(storyService.getById(storyid));
    }

    @GetMapping("/getStoryTag")
    public Result getStoryTag(@RequestParam String storyid){
        return Result.success(storyService.getStoryTag(storyid));
    }
    @GetMapping("/getStoryTagid")
    public Result getStoryTagid(@RequestParam String storyid){
        return Result.success(storyService.getStoryTagid(storyid));
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
    public Result saveStory(@RequestBody StoryNewDto story){
        try{
            Story one = new Story();
            one.setId(story.getId());
            one.setStoryName(story.getStoryName());
            one.setUserId(story.getUserId());
            one.setIntroduce(story.getIntroduce());
            one.setCoverUrl(story.getCoverUrl());
            storyService.saveOrUpdate(one);
            List<Long> tags = story.getTags();
            System.out.println(tags);
            if(tags!= null && !tags.isEmpty()) {
                List<StoryTag> list = new ArrayList<>();
                for (Long tagId:tags) {
                    StoryTag storyTag = new StoryTag();
                    storyTag.setStoryId(one.getId());
                    storyTag.setTagId(tagId);
                    list.add(storyTag);
                }
                storyTagService.saveOrUpdateBatch(list);
            }
            return Result.success(one.getId());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"数据缺失");
        }
    }

    @GetMapping("/checkCollect")
    public Result checkCollect(@RequestParam String userId ,
                               @RequestParam String storyId){
        return storyService.checkCollect(userId,storyId);
    }
    @PostMapping("/deleteStory")
    public  Result deleteStory(@RequestParam String storyId,
                               @RequestParam String token,
                               @RequestParam String userId){
        return storyService.deleteStory(storyId,userId,token);
    }


}
