package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.entity.Fragment;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.service.FragmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fragment")
public class FragmentController {
    @Autowired
    private FragmentService fragmentService;

    /**
     * 获取故事信息
     * @param storyId
     * @return Result
     * */
    @GetMapping("/loadAllFragment")
    public Result loadAllFragment(@RequestParam long storyId){
        return fragmentService.loadAllFragment(storyId);
    }

    /**
     * 获取评论区和作者信息
     * @param fragmentId
     * @return Result
     * */
    @GetMapping("/loadAuthorAndComment")
    public Result loadAuthorAndComment(@RequestParam long fragmentId){
        return fragmentService.loadAuthorAndComment(fragmentId);
    }

    @GetMapping("/getFageInfo")
    public Result getFageInfo(@RequestParam String userid){
        return fragmentService.getFageInfo(userid);
    }






}
