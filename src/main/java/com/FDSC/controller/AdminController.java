package com.FDSC.controller;

import com.FDSC.common.Result;
import com.FDSC.entity.Tag;
import com.FDSC.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
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

}
