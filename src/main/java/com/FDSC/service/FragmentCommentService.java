package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Fragment;
import com.FDSC.entity.FragmentComment;
import com.FDSC.mapper.FragmentCommentMapper;
import com.FDSC.mapper.FragmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.ws.server.ServerRtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
