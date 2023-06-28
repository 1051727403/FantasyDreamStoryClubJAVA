package com.FDSC.service;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.FragmentDto;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.Story;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.mapper.StoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FragmentService extends ServiceImpl<FragmentMapper, Fragment> {

    @Autowired
    private FragmentMapper fragmentMapper;

    //导入redis
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String STORY_KEY="STORY_ID";


    public FragmentDto fragmentToFragmentDto(List<Fragment> fragments){
        //根节点，同时也是返回数据的类型
        FragmentDto fragmentDto=new FragmentDto();
        Map<Long, FragmentDto.ChildDto> nodeMap=new HashMap<>();
        //创建节点映射表
        for(Fragment node:fragments){
            if(node.getParentId()==0){
                //若为根节点
                fragmentDto.setId("root");
                fragmentDto.setRootId(node.getId());
                fragmentDto.setTopic(node.getFragmentName());
                fragmentDto.setContent(node.getContent());
                fragmentDto.setTotalLike(node.getTotalLike());
                fragmentDto.setTotalCollection(node.getTotalCollection());
                fragmentDto.setTotalComment(node.getTotalComment());
                //设置空字段
                fragmentDto.setAuthor(new FragmentDto.AuthorDTO());
                fragmentDto.setComments(new ArrayList<>());
                fragmentDto.setChildren(new ArrayList<>());
            }else {
                FragmentDto.ChildDto childDto=new FragmentDto.ChildDto();
                childDto.setId(node.getId());
                childDto.setTopic(node.getFragmentName());
                childDto.setContent(node.getContent());
                childDto.setTotalLike(node.getTotalLike());
                childDto.setTotalCollection(node.getTotalCollection());
                childDto.setTotalComment(node.getTotalComment());
                //设置空字段
                childDto.setAuthor(new FragmentDto.AuthorDTO());
                childDto.setComments(new ArrayList<>());
                childDto.setChildren(new ArrayList<>());
                nodeMap.put(node.getId(), childDto);
            }
        }

        for(Fragment node:fragments) {
            if(node.getParentId()==0){
                //根节点

            }else{
                FragmentDto.ChildDto child=nodeMap.get(node.getId());
                FragmentDto.ChildDto parent=nodeMap.get(node.getParentId());
                if(parent==null){
                    //若不存在父节点，说明父节点为根节点，自身为子节点，将其加入到父节点的child
                    if(fragmentDto.getChildren()==null){
                        fragmentDto.setChildren(new ArrayList<>());
                    }
                    fragmentDto.getChildren().add(child);
                }else{
                    //存在父节点且不是root
                    if(parent.getChildren()==null){
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(child);
                }
            }
        }
        return fragmentDto;
    }
    public Result loadAllFragment(long storyId) {
        Map<String,Object>res=new HashMap<>();
        //先检查redis中是否存在数据
        String STORY_KEY_ID=STORY_KEY+Long.toString(storyId);
        String jsonStr = stringRedisTemplate.opsForValue().get(STORY_KEY_ID);
        FragmentDto fragmentDto=new FragmentDto();
        if (StrUtil.isBlank(jsonStr)){
            //若为空，则去数据库中查询
            QueryWrapper<Fragment> wrapper = new QueryWrapper<>();
            wrapper.eq("story_id" , storyId);
            List<Fragment> fragments = fragmentMapper.selectList(wrapper);
            //转换格式
            fragmentDto=fragmentToFragmentDto(fragments);
            System.out.println("-------------------------"+fragmentDto);
            //缓存到redis
            //stringRedisTemplate.opsForValue().set(STORY_KEY_ID, JSONUtil.toJsonStr(fragmentDto));

        }else{
            //直接从redis中取出数据
            fragmentDto=JSONUtil.toBean(jsonStr, new TypeReference<FragmentDto>() {
            }, true);

        }
        res.put("data",fragmentDto);
        return Result.success(res);

    }

}
