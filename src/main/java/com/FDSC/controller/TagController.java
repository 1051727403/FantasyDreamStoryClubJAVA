package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private   TagService tagService;

    @GetMapping("/getTags")
    public Result getTags(){
        return tagService.getTags();
    }

}
