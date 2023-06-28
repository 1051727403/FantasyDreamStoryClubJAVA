package com.FDSC.service;


import com.FDSC.entity.Story;
import com.FDSC.entity.User;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class StoryService extends ServiceImpl<StoryMapper, Story> {

    @Autowired
    private StoryMapper storyMapper;

    public List<String> getstorytag(String storyid){return storyMapper.gettag(storyid);}




}
