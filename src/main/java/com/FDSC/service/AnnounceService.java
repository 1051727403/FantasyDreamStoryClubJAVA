package com.FDSC.service;

import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.ActivityDto;
import com.FDSC.controller.dto.AnnounceDto;
import com.FDSC.controller.dto.AnnounceShowDto;
import com.FDSC.entity.Announcement;
import com.FDSC.mapper.AnnounceMapper;
import com.FDSC.mapper.dto.ActivityTempDto;
import com.FDSC.mapper.dto.AnnounceShowTempDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnnounceService extends ServiceImpl<AnnounceMapper, Announcement> {

    @Autowired
    private AnnounceMapper announceMapper;

    public Result slideShow() {
        try{
            return Result.success(announceMapper.getSlideShow());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result twoAnnounce() {
        try{
            List<AnnounceShowTempDto> list = announceMapper.getTwoAnnounce();
            List<AnnounceShowDto> announces = new ArrayList<>(list.size());
            for (AnnounceShowTempDto announce : list) {
                AnnounceShowDto temp = new AnnounceShowDto();
                temp.setAnnounceId(announce.getId());
                temp.setTitle(announce.getTitle());
                temp.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(announce.getCreateTime()));
                announces.add(temp);
            }
            return Result.success(announces);
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result allAnnounce(Integer page) {
        try{
            List<Announcement> list = announceMapper.getAllAnnounce(page, 10, "", 0);
            List<AnnounceDto> announces = new ArrayList<>(list.size());
            for (Announcement announce : list) {
                AnnounceDto temp = new AnnounceDto();
                temp.setAnnounceId(announce.getId());
                temp.setTitle(announce.getTitle());
                temp.setContent(announce.getContent());
                temp.setCoverUrl(announce.getCoverUrl());
                temp.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(announce.getCreateTime()));
                announces.add(temp);
            }
            return Result.success(announces);
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result allAnnounce(Integer page, Integer pageSize, String search, Integer isActivity) {
        try{
            return Result.success(announceMapper.getAllAnnounce(page, pageSize, search, isActivity));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result allActivity(Integer page, Integer pageSize, String search) {
        try{
            List<ActivityTempDto> list = announceMapper.getAllActivity(page, pageSize, search);
            List<ActivityDto> res = new ArrayList<>(list.size());
            for (ActivityTempDto activityTempDto : list) {
                ActivityDto temp = new ActivityDto();
                temp.setId(activityTempDto.getId());
                temp.setTitle(activityTempDto.getTitle());
                temp.setContent(activityTempDto.getContent());
                temp.setCoverUrl(activityTempDto.getCoverUrl());
                temp.setShown(activityTempDto.getTempId() != null);
                res.add(temp);
            }
            return Result.success(res);
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result getAnnounce(Integer id) {
        try{
            Announcement announce = announceMapper.getAnnounce(id);
            AnnounceDto temp = new AnnounceDto();
            temp.setAnnounceId(announce.getId());
            temp.setContent(announce.getContent());
            temp.setCoverUrl(announce.getCoverUrl());
            temp.setTitle(announce.getTitle());
            temp.setUpdateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(announce.getUpdateTime()));
            temp.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(announce.getCreateTime()));
            return Result.success(temp);
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result announceNum() {
        try{
            return Result.success(announceMapper.getAnnounceNum());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

    public Result activityNum() {
        try{
            return Result.success(announceMapper.getActivityNum());
        }catch (Exception e){
            return Result.error(Constants.CODE_500,"获取失败");
        }
    }

}
