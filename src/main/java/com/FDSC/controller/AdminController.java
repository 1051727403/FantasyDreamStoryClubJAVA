package com.FDSC.controller;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.AnnounceDto;
import com.FDSC.controller.dto.SlideShowAnnounceDto;
import com.FDSC.controller.dto.StoryNewDto;
import com.FDSC.controller.dto.UserInfoDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private StoryTagService storyTagService;

    @PostMapping("/upUserInfo")
    public Result upUserInfo(@RequestBody UserInfoDto user) {
        return userService.upUserInfo(user);
    }
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
        return storyService.page(page, queryWrapper);
    }
    @PostMapping("/deleteStory")
    public Result deleteStory(@RequestParam String storyId){
        return adminService.deleteStory(storyId);
    }

    @GetMapping("/announcePage")
    public Result announcePage(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam (defaultValue = "")String search){
        return announceService.allAnnounce(pageNum, pageSize, search, 0);
    }

    @GetMapping("/activityPage")
    public Result activityPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam (defaultValue = "")String search){
        return announceService.allActivity(pageNum, pageSize, search);
    }

    @GetMapping("/announceNum")
    public Result announceNum() { return announceService.announceNum(); }

    @GetMapping("/activityNum")
    public Result activityNum() { return announceService.activityNum(); }

    @PostMapping("/deleteAnnoucnce")
    public Result deleteAnnoucnce(@RequestParam Integer announceId){
        return adminService.deleteAnnounce(announceId);
    }

    @PostMapping("/deleteBatchAnnounce")
    public Result deleteBatchAnnounce(@RequestBody List<Integer> announceIds){
        return adminService.deleteBatchAnnounce(announceIds);
    }

    @PostMapping("/upAnnounceInfo")
    public Result upAnnounceInfo(@RequestBody Announcement announceDto) {
        return adminService.upAnnounceInfo(announceDto);
    }

    @PostMapping("/upActivityInfo")
    public Result upActivityInfo(@RequestBody SlideShowAnnounceDto slideShowAnnounceDto) {
        return adminService.upActivityInfo(slideShowAnnounceDto);
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
    @PostMapping("/fragment/saveFragment")
    public Result saveFragment(@RequestBody Fragment fragment){
        return fragmentService.saveFragment(fragment);
    }

    /**
     * 管理员删除片段
     * */
    @GetMapping("/fragment/deleteFragment")
    public Result saveFragment(@RequestParam Long fragmentId){
        return fragmentService.deleteFragment(fragmentId);
    }

    @PostMapping("/saveStory")
    public Result saveStory(@RequestBody StoryNewDto story){
        try{
            Story one = new Story();
            one.setId(story.getId());
            one.setStoryName(story.getStoryName());
            one.setUserId(story.getUserId());
            one.setIntroduce(story.getIntroduce());
            one.setCoverUrl(story.getCoverUrl());
            storyService.saveOrUpdate(one);
            List<Long> tags = story.getTags();
            System.out.println(tags);
            if(tags!= null && !tags.isEmpty()) {
                List<StoryTag> list = new ArrayList<>();
                for (Long tagId:tags) {
                    StoryTag storyTag = new StoryTag();
                    storyTag.setStoryId(one.getId());
                    storyTag.setTagId(tagId);
                    list.add(storyTag);
                }
                storyTagService.saveOrUpdateBatch(list);
            }
            return Result.success(one.getId());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"数据缺失");
        }
    }

}
