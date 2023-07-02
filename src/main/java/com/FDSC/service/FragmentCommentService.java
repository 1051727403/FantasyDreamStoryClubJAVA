package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.FragmentComment;
import com.FDSC.mapper.FragmentCommentMapper;
import com.FDSC.mapper.FragmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.ws.server.ServerRtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FragmentCommentService extends ServiceImpl<FragmentCommentMapper, FragmentComment> {

    @Autowired
    private FragmentCommentMapper fragmentCommentMapper;


    public Result saveComment(FragmentComment fragmentComment) {
        try {
            fragmentCommentMapper.insert(fragmentComment);
            if (fragmentComment.getParentId()==0){
                //插入新主题
                fragmentComment.setTopicId(fragmentComment.getId());
                fragmentCommentMapper.updateById(fragmentComment);
            }
            return Result.success();
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"保存失败！");

        }


    }

    //删除评论
    public Result deleteById(Long id) {
        QueryWrapper<FragmentComment> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<FragmentComment>fragmentCommentList=new ArrayList<>();
        try {
            fragmentCommentList=fragmentCommentMapper.selectList(wrapper);
        }catch (Exception e){
            throw new ServerRtException(Constants.CODE_500,"获取子评论失败");
        }
        if (fragmentCommentList==null||fragmentCommentList.size()==0){
            //若无子评论则直接删除
            try {
                fragmentCommentMapper.deleteById(id);
                return Result.success();
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"删除评论失败！");
            }
        }else{
            //若有子评论则更新为：该评论已删除
            try {
                String content="该评论已删除！";
                fragmentCommentMapper.updateContent(id,content);
                return Result.success();
            }catch (Exception e){
                throw new ServerRtException(Constants.CODE_500,"删除评论失败！");
            }
        }
    }
}
