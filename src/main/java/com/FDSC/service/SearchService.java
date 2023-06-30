package com.FDSC.service;

import com.FDSC.common.Result;
import com.FDSC.controller.dto.StoryDto;
import com.FDSC.controller.dto.TagDto;
import com.FDSC.mapper.StoryMapper;
import com.FDSC.mapper.TagMapper;
import com.FDSC.mapper.dto.StoryTempDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private StoryMapper storyMapper;

    public Result allTags() {
        try{
            return Result.success(tagMapper.getAll());
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

    private String formatDateTime(LocalDateTime time) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dtf2.format(time);
    }

    public Result search(Long tag, String sort) {
        try{
            System.out.println(tag);
            System.out.println(sort);

            if (Objects.equals(sort, "date")) sort = "update_time";
            else if (Objects.equals(sort, "liked")) sort = "total_like";
            else if (Objects.equals(sort, "collection")) sort = "total_collection";
            else throw new Exception();

            System.out.println(tag);
            System.out.println(sort);

            List<StoryTempDto> list = storyMapper.search(tag, sort);
            List<StoryDto> stories = new ArrayList<>(list.size());
            Long currentId = (long) -1;
            List<TagDto> currentTagList = null;

            for (StoryTempDto item : list) {
                if (!Objects.equals(item.getId(), currentId)) {
                    currentId = item.getId();
                    currentTagList = new ArrayList<>();

                    StoryDto temp = new StoryDto();
                    temp.setStoryId(item.getId());
                    temp.setUserId(item.getUserId());
                    temp.setTotalLike(item.getTotalLike());
                    temp.setTotalCollection(item.getTotalCollection());
                    temp.setTotalComment(item.getTotalComment());
                    temp.setCoverUrl(item.getCoverUrl());
                    temp.setStoryName(item.getStoryName());
                    temp.setIntroduce(item.getIntroduce());
                    temp.setTags(currentTagList);
                    temp.setCreateTime(formatDateTime(item.getCreateTime()));
                    temp.setUpdateTime(formatDateTime(item.getUpdateTime()));
                    stories.add(temp);
                }
                if (item.getTagId() != null) {
                    TagDto temp = new TagDto();
                    temp.setTagId(item.getTagId());
                    temp.setTagName(item.getTagName());
                    assert currentTagList != null;
                    currentTagList.add(temp);
                }
            }
            System.out.println(stories);
            return Result.success(stories);
        }catch (Exception e){
            return Result.error("403","获取失败");
        }
    }

}
