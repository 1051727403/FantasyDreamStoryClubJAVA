package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.Tag;
import com.FDSC.entity.User;
import com.FDSC.service.AdminService;
import com.FDSC.service.FragmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private FragmentService fragmentService;
    @Autowired
    private AdminService adminService;
    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestParam String userId){
        return adminService.deleteUser(userId);
    }
    @PostMapping("/deleteBatchUser")
    public Result deleteBatchUser(@RequestBody List<Integer> userIds){
        return adminService.deleteBatchUser(userIds);
    }

    @PostMapping("/deleteTag")
    public Result deleteTag(@RequestParam String tagId){
        return adminService.deleteTag(tagId);
    }
    @PostMapping("/deleteBatchTag")
    public Result deleteBatchTag(@RequestBody List<Integer> tagIds){
        return adminService.deleteBatchTag(tagIds);
    }

    @PostMapping("/addTag")
    public Result addOrUpdateTag(@RequestBody Tag tag){return adminService.addOrUpdateTag(tag);}

    @GetMapping("/fragment/page")
    public IPage<Fragment> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<Fragment> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.like("id",search);
        queryWrapper.or();
        queryWrapper.like("user_id",search);
        queryWrapper.or();
        queryWrapper.like("parent_id",search);
        queryWrapper.or();
        queryWrapper.like("fragment_name",search);
        queryWrapper.or();
        queryWrapper.like("content",search);
        queryWrapper.orderByDesc("id");
        //后台获取用户登录信息
        return fragmentService.page(page,queryWrapper);
    }

}
