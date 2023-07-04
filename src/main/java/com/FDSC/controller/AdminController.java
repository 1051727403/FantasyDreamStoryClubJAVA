package com.FDSC.controller;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.*;
import com.FDSC.service.*;
import com.FDSC.service.AdminService;
import com.FDSC.service.FragmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.FDSC.service.StoryService;
import com.FDSC.service.TagService;
import com.FDSC.service.UserService;
import com.FDSC.utils.TokenUtils;
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
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private StoryService storyService;
    @Autowired
    private AnnounceService announceService;

    @GetMapping("/userPage")
    public IPage<User> userPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<User>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("username",search);
        queryWrapper.or();
        queryWrapper.like("nickname",search);
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);
    }
    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestParam String userId){
        return adminService.deleteUser(userId);
    }
    @PostMapping("/deleteBatchUser")
    public Result deleteBatchUser(@RequestBody List<Integer> userIds){
        return adminService.deleteBatchUser(userIds);
    }

    @GetMapping("/tagPage")
    public IPage<Tag> tagPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<Tag> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("tag_name",search);
        queryWrapper.orderByDesc("id");
        return tagService.page(page,queryWrapper);
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
    public Result addOrUpdateTag(@RequestBody Tag tag){
        try{
            return Result.success(tagService.saveOrUpdate(tag));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    @GetMapping("/storyPage")
    public IPage<Story> storyPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<Story> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<Story>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("story_name",search);
        queryWrapper.or();
        queryWrapper.like("introduce",search);
        queryWrapper.orderByDesc("id");
        return storyService.page(page,queryWrapper);
    }
    @PostMapping("/deleteStory")
    public Result deleteStory(@RequestParam String storyId){
        return adminService.deleteStory(storyId);
    }

    @GetMapping("/announcePage")
    public IPage<Announcement> announcePage(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<Story> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<Story>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("title",search);
        queryWrapper.or();
        queryWrapper.like("content",search);
        queryWrapper.orderByDesc("id");
        return storyService.page(page,queryWrapper);
    }

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
