package com.FDSC.controller;

import com.FDSC.common.Result;
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


}
