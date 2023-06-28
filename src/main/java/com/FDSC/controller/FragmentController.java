package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.service.FragmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fragment")
public class FragmentController {

    @Autowired
    private FragmentService fragmentService;
    @Autowired
    private FragmentMapper fragmentMapper;


    @GetMapping("/loadAllFragment")
    public Result loadAllFragment(@RequestParam long storyId){
        return fragmentService.loadAllFragment(storyId);
    }






}
