package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.entity.FragmentComment;
import com.FDSC.service.FragmentCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fragmentComment")
public class FragmentCommentController {

    @Autowired
    private FragmentCommentService fragmentCommentService;



    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody FragmentComment fragmentComment){
        return fragmentCommentService.saveComment(fragmentComment);
    }





}
