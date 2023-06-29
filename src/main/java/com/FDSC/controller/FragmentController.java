package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.controller.dto.AddFragmentDto;
import com.FDSC.entity.Fragment;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.service.FragmentService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/fragment")
public class FragmentController {
    @Autowired
    private FragmentService fragmentService;

    /**
     * 获取故事信息
     * @param storyId
     * @return Result
     * */
    @GetMapping("/loadAllFragment")
    public Result loadAllFragment(@RequestParam long storyId){
        return fragmentService.loadAllFragment(storyId);
    }

    /**
     * 获取评论区和作者信息
     * @param fragmentId
     * @return Result
     * */
    @GetMapping("/loadauthorInfoAndComment")
    public Result loadAuthorAndComment(@RequestParam long fragmentId){
        return fragmentService.loadAuthorAndComment(fragmentId);
    }
    /**
     * 添加片段接龙
     * @param addFragmentDto
     * @return Result
     * */
    @PostMapping("/addFragment")
    public Result addFragment(@RequestBody AddFragmentDto addFragmentDto){
        return fragmentService.addFragment(addFragmentDto);
    }

    @GetMapping("/getFageInfo")
    public Result getFageInfo(@RequestParam String userid){
        return fragmentService.getFageInfo(userid);
    }




}
