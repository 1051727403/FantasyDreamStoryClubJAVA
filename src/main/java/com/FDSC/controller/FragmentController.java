package com.FDSC.controller;


import com.FDSC.common.Result;
import com.FDSC.controller.dto.AddFragmentDto;
import com.FDSC.controller.dto.UpdateFragmentDto;
import com.FDSC.entity.Fragment;
import com.FDSC.service.FragmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result loadAllFragment(@RequestParam Long storyId){
        return fragmentService.loadAllFragment(storyId);
    }

    /**
     * 获取评论区和作者信息以及是否点赞收藏的信息
     * @param fragmentId
     * @return Result
     * */
    @GetMapping("/loadauthorInfoAndComment")
    public Result loadAuthorAndComment(@RequestParam Long fragmentId,
                                       @RequestParam Long userId){
        return fragmentService.loadAuthorAndComment(fragmentId,userId);
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
    /**
     * 添加片段接龙
     * @param addFragmentDto
     * @return Result
     * */
    @PostMapping("/addRootFragment")
    public Result addRootFragment(@RequestBody AddFragmentDto addFragmentDto){
        Fragment fragment = new Fragment();
        BeanUtils.copyProperties(addFragmentDto,fragment);
        return Result.success(fragmentService.save(fragment));
    }
    /**
     * 修改片段
     * @param updateFragmentDto
     * @return Result
     * */
    @PostMapping("/updateFragment")
    public Result updateFragment(@RequestBody UpdateFragmentDto updateFragmentDto){
        return fragmentService.updateFragment(updateFragmentDto);
    }

    /**
     * 删除片段
     * @param fragmentId
     * @return Result
     * */
    @DeleteMapping("/deleteFragment")
    public Result deleteFragment(@RequestParam Long fragmentId){
        return fragmentService.deleteFragment(fragmentId);
    }

    /**
     * 片段点赞以及取消点赞
     * @param userId,fragmentId,beLike
     * @return Result
     * */
    @GetMapping("/changeLike")
    public Result changeLike(@RequestParam Long userId,
                             @RequestParam Long fragmentId,
                             @RequestParam boolean beLike){
        return fragmentService.changeLike(userId,fragmentId,beLike);
    }

    /**
     * 片段收藏以及取消收藏
     * @param userId,fragmentId,beCollection
     * @return Result
     * */
    @GetMapping("/changeCollection")
    public Result changeCollection(@RequestParam Long userId,
                             @RequestParam Long fragmentId,
                             @RequestParam boolean beCollection){
        return fragmentService.changeCollection(userId,fragmentId,beCollection);
    }

    @GetMapping("/getUserFrag")
    public Result getUserFrag(@RequestParam String userId){
        return fragmentService.getUserFrag(userId);
    }

    @GetMapping("/getCollectFrag")
    public Result getCollectFrag(@RequestParam String userId){
        return fragmentService.getCollectFrag(userId);
    }




}
