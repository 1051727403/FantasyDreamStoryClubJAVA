package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.entity.FragmentComment;
import com.FDSC.service.FragmentCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fragmentComment")
public class FragmentCommentController {

    @Autowired
    private FragmentCommentService fragmentCommentService;

    /**
     * 添加新的回复
     * @param fragmentComment
     * @return Result
     * */
    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody FragmentComment fragmentComment){
        return fragmentCommentService.saveComment(fragmentComment);
    }

    /**
     * 删除评论，若有回复则更新内容为：评论已删除，若无则直接删除
     * @param id
     * @return Result
     * */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam Long id){
        return fragmentCommentService.deleteById(id);
    }




}
