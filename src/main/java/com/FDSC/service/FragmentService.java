package com.FDSC.service;

import com.FDSC.entity.Fragment;
import com.FDSC.entity.Story;
import com.FDSC.mapper.FragmentMapper;
import com.FDSC.mapper.StoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FragmentService extends ServiceImpl<FragmentMapper, Fragment> {

    @Autowired
    private FragmentMapper fragmentMapper;

    public Map<String,Object> loadAllFragment(long storyId) {
        Map<String,Object>res=new HashMap<>();







        return res;
    }

}
