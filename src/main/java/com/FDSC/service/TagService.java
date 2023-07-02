package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagService {

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
