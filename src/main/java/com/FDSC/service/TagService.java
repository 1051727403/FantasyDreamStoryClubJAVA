package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Tag;
import com.FDSC.entity.User;
import com.FDSC.mapper.TagMapper;
import com.FDSC.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagService extends ServiceImpl<TagMapper, Tag> {

    @Autowired
    private TagMapper tagMapper;
    public Result getTags() {
        try{
            return Result.success(tagMapper.getAll());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"数据库错误");
        }
    }
}
