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

    @GetMapping("/page")
    public IPage<Tag> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<Tag> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("tag_name",search);
        queryWrapper.orderByDesc("id");
        //后台获取用户登录信息
        User currentUser= TokenUtils.getUserInfo();
        System.out.println("=========================当前用户信息:"+currentUser.getNickname());
        return tagService.page(page,queryWrapper);
    }

}
