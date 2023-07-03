package com.FDSC.service;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.AddFragmentDto;
import com.FDSC.controller.dto.FragmentDto;
import com.FDSC.controller.dto.UpdateFragmentDto;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.FragmentLikeCollection;
import com.FDSC.mapper.FragmentLikeCollectionMapper;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.dto.FragmentAndUserInfo;
import com.FDSC.mapper.dto.FragmentMapperCommentDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.ws.server.ServerRtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FragmentService extends ServiceImpl<FragmentMapper, Fragment> {

    @Autowired
    private FragmentMapper fragmentMapper;

    @Autowired
    private StoryMapper storyMapper;

    //导入redis
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FragmentLikeCollectionMapper fragmentLikeCollectionMapper;

    public String STORY_KEY="STORY_ID";


    public FragmentDto fragmentToFragmentDto(List<FragmentAndUserInfo> fragmentAndUserInfoList){
        //根节点，同时也是返回数据的类型
        FragmentDto fragmentDto=new FragmentDto();
        Map<Long, FragmentDto.ChildDto> nodeMap=new HashMap<>();
        //创建节点映射表
        for(FragmentAndUserInfo node:fragmentAndUserInfoList){
            if(node.getParentId()==0){
                //若为根节点
                fragmentDto.setId("root");
                fragmentDto.setRootId(node.getId());
                fragmentDto.setTopic(node.getFragmentName());
                fragmentDto.setAllowRelay(node.getAllowRelay());
                fragmentDto.setContent(node.getContent());
                fragmentDto.setTotalLike(node.getTotalLike());
                fragmentDto.setTotalCollection(node.getTotalCollection());
                fragmentDto.setTotalComment(node.getTotalComment());
                //设置空字段
                fragmentDto.setAuthorInfo(new FragmentDto.AuthorDTO(node.getUserId(),node.getNickname(),node.getAvatarUrl(),node.getAuthorTotalLike()));
                fragmentDto.setComments(new ArrayList<>());
                fragmentDto.setChildren(new ArrayList<>());
            }else {
                FragmentDto.ChildDto childDto=new FragmentDto.ChildDto();
                childDto.setId(node.getId());
                childDto.setTopic(node.getFragmentName());
                childDto.setAllowRelay(node.getAllowRelay());
                childDto.setContent(node.getContent());
                childDto.setTotalLike(node.getTotalLike());
                childDto.setTotalCollection(node.getTotalCollection());
                childDto.setTotalComment(node.getTotalComment());
                //设置空字段
                childDto.setAuthorInfo(new FragmentDto.AuthorDTO(node.getUserId(),node.getNickname(),node.getAvatarUrl(),node.getAuthorTotalLike()));
                childDto.setComments(new ArrayList<>());
                childDto.setChildren(new ArrayList<>());
                nodeMap.put(node.getId(), childDto);
            }
        }

        for(FragmentAndUserInfo node:fragmentAndUserInfoList) {
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
//        String jsonStr = stringRedisTemplate.opsForValue().get(STORY_KEY_ID);
        String jsonStr=null;
        FragmentDto fragmentDto=new FragmentDto();
        String storyName="";
        if (StrUtil.isBlank(jsonStr)){
            //若为空，则去数据库中查询
            List<FragmentAndUserInfo> fragmentAndUserInfoList = fragmentMapper.selectByStoryId(storyId);
            //转换格式
            fragmentDto=fragmentToFragmentDto(fragmentAndUserInfoList);
            System.out.println("-------------------------"+fragmentDto);
            //缓存到redis
            //stringRedisTemplate.opsForValue().set(STORY_KEY_ID, JSONUtil.toJsonStr(fragmentDto));
            storyName=storyMapper.selectById(storyId).getStoryName();
        }else{
            //直接从redis中取出数据
//            fragmentDto=JSONUtil.toBean(jsonStr, new TypeReference<FragmentDto>() {
//            }, true);

        }
        res.put("data",fragmentDto);
        res.put("storyName",storyName);
        return Result.success(res);

    }

    private List<FragmentDto.CommentDTO> listToComment(List<FragmentMapperCommentDto> fragmentMapperCommentDtoList) {
        Map<Long, FragmentDto.CommentDTO>topicMap=new HashMap<>();
        Map<Long, FragmentMapperCommentDto>NodeMap=new HashMap<>();
        List<FragmentDto.CommentDTO>result=new ArrayList<>();

        for(FragmentMapperCommentDto comment:fragmentMapperCommentDtoList){
            if(comment.getToId()==0){
                //主题节点，转换后直接放入result
                FragmentDto.CommentDTO commentDTO=new FragmentDto.CommentDTO();
                commentDTO.setUserId(comment.getUserId());
                commentDTO.setName(comment.getNickname());
                commentDTO.setId(comment.getFromId());
                commentDTO.setHeadImg(comment.getAvatarUrl());
                commentDTO.setComment(comment.getContent());
                commentDTO.setTime(comment.getTime());
                commentDTO.setInputShow(false);
                commentDTO.setTopicId(comment.getTopicId());
                commentDTO.setReply(new ArrayList<>());
                result.add(commentDTO);
                topicMap.put(comment.getFromId(),commentDTO);
            }
            NodeMap.put(comment.getFromId(),comment);
        }

        for(FragmentMapperCommentDto comment:fragmentMapperCommentDtoList){
            if(comment.getToId()==0){
                //主题节点跳过
            }else{
                FragmentDto.CommentDTO topicNode=topicMap.get(comment.getTopicId());
                FragmentMapperCommentDto parentNode=NodeMap.get(comment.getToId());

                FragmentDto.CommentDTO.ReplyDTO replyDTO=new FragmentDto.CommentDTO.ReplyDTO();
                replyDTO.setFrom(comment.getNickname());
                replyDTO.setUserId(comment.getUserId());
                replyDTO.setFromId(comment.getFromId());
                replyDTO.setFromHeadImg(comment.getAvatarUrl());
                replyDTO.setTo(parentNode.getNickname());
                replyDTO.setToId(parentNode.getToId());
                replyDTO.setComment(comment.getContent());
                replyDTO.setTime(comment.getTime());
                replyDTO.setInputShow(false);

                topicNode.getReply().add(replyDTO);
            }

        }

        return result;
    }
    //获取片段作者信息
    public Result loadAuthorAndComment(Long fragmentId,Long userId) {
        Map<String,Object>res=new HashMap<>();
        //获取片段作者信息
        FragmentDto.AuthorDTO author=new FragmentDto.AuthorDTO();
        author=fragmentMapper.getFragmentAuthor(fragmentId);
        res.put("authorInfo",author);
        //获取评论
        List<FragmentDto.CommentDTO> comment=new ArrayList<>();
        //获取对应片段评论
        List<FragmentMapperCommentDto> fragmentMapperCommentDtoList=fragmentMapper.getFragmentComments(fragmentId);
        //转换格式
        comment=listToComment(fragmentMapperCommentDtoList);
        res.put("comments",comment);
        if (userId==null){
            res.put("isLike",0);
            res.put("isCollected",0);
        }else {
            //获取是否点赞和收藏
            Integer isLike;
            Integer isCollected;
            QueryWrapper<FragmentLikeCollection> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("fragment_id", fragmentId);
            FragmentLikeCollection fragmentLikeCollection = new FragmentLikeCollection();
            try {
                fragmentLikeCollection = fragmentLikeCollectionMapper.selectOne(wrapper);
            } catch (Exception e) {
                throw new ServerRtException(Constants.CODE_500, "是否已点赞表中存在脏数据");
            }
            if (fragmentLikeCollection == null) {
                isLike = isCollected = 0;
            } else {
                isLike = fragmentLikeCollection.getIsLike();
                isCollected = fragmentLikeCollection.getIsCollection();
            }
            res.put("isLike", isLike);
            res.put("isCollected", isCollected);
        }
        //获取评论数
        Integer totalComment;
        try {
            totalComment=fragmentMapper.selectById(fragmentId).getTotalComment();
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"评论数获取失败！");
        }
        res.put("totalComment",totalComment);
        return Result.success(res);
    }
    public Result getUserFrag(String userid) {
        try{
            return Result.success(fragmentMapper.usersfrag(userid));
        }
        catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }
    //插入非根节点
    public Result addFragment(AddFragmentDto addFragmentDto) {
        Fragment fragment=new Fragment();
        fragment.setUserId(addFragmentDto.getUserId());
        fragment.setStoryId(addFragmentDto.getStoryId());
        fragment.setParentId(addFragmentDto.getParentId());
        fragment.setFragmentName(addFragmentDto.getFragmentName());
        fragment.setContent(addFragmentDto.getContent());
        fragment.setAllowRelay(addFragmentDto.getAllowRelay());
        //检测是否允许接龙
        //TODO
        //获取父节点的allowrelay字段进行判断
        Fragment parent=fragmentMapper.selectById(addFragmentDto.getParentId());
        if(parent.getAllowRelay()==0&&parent.getUserId()!=addFragmentDto.getUserId()){
            throw new ServerRtException(Constants.CODE_500,"该片段不可接龙，请刷新！");
        }else {
            try {
                save(fragment);
                return Result.success(fragment);
            } catch (Exception e) {
                throw new ServerRtException(Constants.CODE_500, "保存片段失败！");
            }
        }
//        return Result.success();
    }
    //删除片段
    public Result deleteFragment(Long fragmentId) {
        try {
            fragmentMapper.deleteById(fragmentId);
            return Result.success();
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"删除失败！");
        }
    }
    //点赞还是取消点赞
    public Result changeLike(Long userId, Long fragmentId,boolean beLike) {
            //是否存在点赞信息
            QueryWrapper<FragmentLikeCollection>wrapper=new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("fragment_id",fragmentId);
            FragmentLikeCollection fragmentLikeCollection= fragmentLikeCollectionMapper.selectOne(wrapper);
            if(fragmentLikeCollection!=null){
                //找到则更新
                try {
                    fragmentLikeCollectionMapper.changeLike(userId,fragmentId,beLike?1:0);
                }catch (Exception e){
                    throw new ServerRtException(Constants.CODE_500,"更新失败！");
                }
            }else{
                //未找到则插入
                try {
                    fragmentLikeCollectionMapper.insertone(userId,fragmentId,beLike?1:0);
                }catch (Exception e){
                    throw new ServerRtException(Constants.CODE_500,"插入失败！");
                }
            }
            return Result.success();
    }
    //收藏还是取消收藏
    public Result changeCollection(Long userId, Long fragmentId, boolean beCollection) {
        //是否存在收藏信息
        QueryWrapper<FragmentLikeCollection>wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("fragment_id",fragmentId);
        FragmentLikeCollection fragmentLikeCollection= fragmentLikeCollectionMapper.selectOne(wrapper);
        if(fragmentLikeCollection!=null){
            //找到则更新
            try {
                fragmentLikeCollectionMapper.changeCollection(userId,fragmentId,beCollection?1:0);
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"更新失败！");
            }
        }else{
            //未找到则插入
            try {
                fragmentLikeCollectionMapper.insertone(userId,fragmentId,beCollection?1:0);
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"插入失败！");
            }
        }
        return Result.success();
    }

    //修改片段信息
    public Result updateFragment(UpdateFragmentDto updateFragmentDto) {
        Fragment fragment=new Fragment();
        try {
            fragment =fragmentMapper.selectById(updateFragmentDto.getFragmentId());
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"不存在该片段，更新失败！");
        }
        fragment.setFragmentName(updateFragmentDto.getFragmentName());
        fragment.setContent(updateFragmentDto.getContent());
        fragment.setAllowRelay(updateFragmentDto.getAllowRelay());
        try {
            fragmentMapper.updateById(fragment);
            return Result.success(fragment);
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"更新片段失败！");
        }
    }

    public Result getCollectFrag(String userid) {
        try{
            return Result.success(fragmentMapper.collectfrag(userid));
        }
        catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }
}
