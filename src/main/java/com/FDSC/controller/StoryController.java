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

    @GetMapping("/getstoryinfo")
    public Result getstory(@RequestParam String storyid){
        return Result.success(storyService.getById(storyid));
    }

    @GetMapping("/getstorytag")
    public Result getstorytag(@RequestParam String storyid){
        return Result.success(storyService.getstorytag(storyid));
    }

    @PostMapping("/collectestory")
    public Result collectestory(@RequestParam String storyid,
                                @RequestParam String userid){

      return storyService.collectestory(storyid,userid);
    };










}
