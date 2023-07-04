package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.entity.Tag;
import com.FDSC.entity.User;
import com.FDSC.service.TagService;
import com.FDSC.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
