package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.FragmentDto;
import com.FDSC.entity.Story;
import com.FDSC.entity.StoryComment;
import com.FDSC.mapper.StoryCommentMapper;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.dto.FragmentMapperCommentDto;
import com.FDSC.mapper.dto.StoryMapperCommentDto;
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
public class StoryCommentService extends ServiceImpl<StoryCommentMapper, StoryComment> {

    @Autowired
    private StoryCommentMapper StoryCommentMapper;

    @Autowired
    private StoryMapper storyMapper;

    public Result saveComment(StoryComment StoryComment) {
        try {
            StoryCommentMapper.insert(StoryComment);
            if (StoryComment.getParentId()==0){
                //插入新主题
                StoryComment.setTopicId(StoryComment.getId());
                StoryCommentMapper.updateById(StoryComment);
            }
            return Result.success();
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"保存失败！");

        }


    }

    //删除评论
    public Result deleteById(Long id) {
        QueryWrapper<StoryComment> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<StoryComment>StoryCommentList=new ArrayList<>();
        try {
            StoryCommentList=StoryCommentMapper.selectList(wrapper);
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"获取子评论失败");
        }
        System.out.println("------------------------------------"+StoryCommentList.size());
        if (StoryCommentList==null||StoryCommentList.size()==0){
            //若无子评论则直接删除
            try {
                StoryCommentMapper.deleteById(id);
                return Result.success();
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"删除评论失败！");
            }
        }else{
            //若有子评论则更新为：该评论已删除
            try {
                String content="该评论已删除！";
                StoryCommentMapper.updateContent(id,content);
                return Result.success();
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"删除评论失败！");
            }
        }
    }
    private List<FragmentDto.CommentDTO> listToComment(List<StoryMapperCommentDto> storyMapperCommentDtoList) {
        Map<Long, FragmentDto.CommentDTO>topicMap=new HashMap<>();
        Map<Long, StoryMapperCommentDto>NodeMap=new HashMap<>();
        List<FragmentDto.CommentDTO>result=new ArrayList<>();

        for(StoryMapperCommentDto comment:storyMapperCommentDtoList){
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

        for(StoryMapperCommentDto comment:storyMapperCommentDtoList){
            if(comment.getToId()==0){
                //主题节点跳过
            }else{
                FragmentDto.CommentDTO topicNode=topicMap.get(comment.getTopicId());
                StoryMapperCommentDto parentNode=NodeMap.get(comment.getToId());

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
    public Result loadStoryComment(long storyId) {
        Map<String,Object> res=new HashMap<>();
        //获取评论
        List<FragmentDto.CommentDTO> comment=new ArrayList<>();
        //获取对应片段评论
        List<StoryMapperCommentDto> storyMapperCommentDtoList=StoryCommentMapper.getStoryComments(storyId);
        //转换格式
        comment=listToComment(storyMapperCommentDtoList);
        res.put("comments",comment);
        //获取评论数
        Integer totalComment;
        try {
            totalComment=storyMapper.selectById(storyId).getTotalComment();
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"评论数获取失败！");
        }
        res.put("totalComment",totalComment);
        return  Result.success(res);
    }
}
