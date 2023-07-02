package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.entity.StoryComment;
import com.FDSC.service.StoryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storyComment")
public class StoryCommentController {

    @Autowired
    private StoryCommentService StoryCommentService;

    /**
     * 添加新的回复
     * @param StoryComment
     * @return Result
     * */
    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody StoryComment StoryComment){
        return StoryCommentService.saveComment(StoryComment);
    }

    /**
     * 删除评论，若有回复则更新内容为：评论已删除，若无则直接删除
     * @param id
     * @return Result
     * */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam Long id){
        return StoryCommentService.deleteById(id);
    }

    /**
     * 获取评论区
     * @param storyId
     * @return Result
     * */
    @GetMapping("/loadStoryComment")
    public Result loadStoryComment(@RequestParam long storyId){
        return StoryCommentService.loadStoryComment(storyId);
    }




}
