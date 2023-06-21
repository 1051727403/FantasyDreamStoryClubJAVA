package com.FDSC.service.impl;

import com.FDSC.entity.User;
import com.FDSC.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.FDSC.controller.dto.ElectricityPaymentDto;
import com.FDSC.entity.ElectricityPayment;
import com.FDSC.mapper.ElectricityPaymentMapper;
import com.FDSC.service.ElectricityPaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author maple
* @description 针对表【sys_electricity_payment】的数据库操作Service实现
* @createDate 2023-02-13 13:16:02
*/
@Service
public class ElectricityPaymentServiceImpl extends ServiceImpl<ElectricityPaymentMapper, ElectricityPayment>
    implements ElectricityPaymentService{

    @Resource
    private ElectricityPaymentMapper electricityPaymentMapper;
    @Resource
    private UserService userService;

    @Override
    public List<ElectricityPaymentDto> getAllInfo() {
        return electricityPaymentMapper.getAllInfo();
    }

    @Override
    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, String search, String state) {
        Page<ElectricityPayment> pageInfo = new Page(pageNum, pageSize);
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = this.getElectricityPaymentQueryWrapper(search, state);
        this.page(pageInfo, queryWrapper);
        if(pageNum > pageInfo.getPages())
        {
            pageInfo.setCurrent(pageInfo.getPages());
            this.page(pageInfo, queryWrapper);
        }
        return pageInfo;
    }

    @Override
    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, String campus, String area, String building, String roomId, String state) {
        Page<ElectricityPayment> pageInfo = new Page(pageNum, pageSize);
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = this.getElectricityPaymentQueryWrapper (campus, area, building, roomId, state);
        this.page(pageInfo, queryWrapper);
        if(pageNum > pageInfo.getPages())
        {
            pageInfo.setCurrent(pageInfo.getPages());
            this.page(pageInfo, queryWrapper);
        }
        return pageInfo;
    }

    @Override
    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, Integer uid, String state) {
        Page<ElectricityPayment> pageInfo = new Page(pageNum, pageSize);
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = this.getElectricityPaymentQueryWrapper(uid, state);
        this.page(pageInfo, queryWrapper);
        if(pageNum > pageInfo.getPages())
        {
            pageInfo.setCurrent(pageInfo.getPages());
            this.page(pageInfo, queryWrapper);
        }
        return pageInfo;
    }

    @Override
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, String search, String state) {
        Page<ElectricityPaymentDto> page = new Page(pageNum, pageSize);
        Page<ElectricityPayment> pageInfo = this.getPageElectricityPayment(pageNum, pageSize, search, state);
        BeanUtils.copyProperties(pageInfo, page, "records");
        List<ElectricityPaymentDto> collect = pageInfo.getRecords().stream().map(a -> {
            ElectricityPaymentDto electricityPaymentDto = new ElectricityPaymentDto();
            User user = userService.getById((a.getUid()));
            electricityPaymentDto.setCreateTime(a.getCreateTime());
            electricityPaymentDto.setStatus(a.getStatus());
            electricityPaymentDto.setUser(user);
            electricityPaymentDto.setMoney(a.getMoney());
            electricityPaymentDto.setRoomId(a.getRoomId());
            electricityPaymentDto.setUid(a.getUid());
            electricityPaymentDto.setBuilding(a.getBuilding());
            electricityPaymentDto.setCampus(a.getCampus());
            electricityPaymentDto.setId(a.getId());
            electricityPaymentDto.setArea(a.getArea());
            return electricityPaymentDto;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return page;
    }

    @Override
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, String campus, String area, String building, String roomId, String state) {
        Page<ElectricityPaymentDto> page = new Page(pageNum, pageSize);
        Page<ElectricityPayment> pageInfo = this.getPageElectricityPayment(pageNum, pageSize, campus, area, building, roomId, state);
        BeanUtils.copyProperties(pageInfo, page, "records");
        List<ElectricityPaymentDto> collect = pageInfo.getRecords().stream().map(a -> {
            ElectricityPaymentDto electricityPaymentDto = new ElectricityPaymentDto();
            User user = userService.getById((a.getUid()));
            electricityPaymentDto.setCreateTime(a.getCreateTime());
            electricityPaymentDto.setStatus(a.getStatus());
            electricityPaymentDto.setUser(user);
            electricityPaymentDto.setMoney(a.getMoney());
            electricityPaymentDto.setRoomId(a.getRoomId());
            electricityPaymentDto.setUid(a.getUid());
            electricityPaymentDto.setBuilding(a.getBuilding());
            electricityPaymentDto.setCampus(a.getCampus());
            electricityPaymentDto.setId(a.getId());
            electricityPaymentDto.setArea(a.getArea());
            return electricityPaymentDto;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return page;
    }

    @Override
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, Integer uid, String state) {
        Page<ElectricityPaymentDto> page = new Page(pageNum, pageSize);
        Page<ElectricityPayment> pageInfo = this.getPageElectricityPayment(pageNum, pageSize, uid, state);
        BeanUtils.copyProperties(pageInfo, page, "records");
        List<ElectricityPaymentDto> collect = pageInfo.getRecords().stream().map(a -> {
            ElectricityPaymentDto electricityPaymentDto = new ElectricityPaymentDto();
            User user = userService.getById((a.getUid()));
            electricityPaymentDto.setCreateTime(a.getCreateTime());
            electricityPaymentDto.setStatus(a.getStatus());
            electricityPaymentDto.setUser(user);
            electricityPaymentDto.setMoney(a.getMoney());
            electricityPaymentDto.setRoomId(a.getRoomId());
            electricityPaymentDto.setUid(a.getUid());
            electricityPaymentDto.setBuilding(a.getBuilding());
            electricityPaymentDto.setCampus(a.getCampus());
            electricityPaymentDto.setId(a.getId());
            electricityPaymentDto.setArea(a.getArea());
            return electricityPaymentDto;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return page;
    }

    @Override
    // 获取根据条件获取电费账单
    public List<ElectricityPayment> getElectricityPayment(String search, String state) {
        List<ElectricityPayment> list = this.list(this.getElectricityPaymentQueryWrapper(search, state));
        return list;
    }

    public  LambdaQueryWrapper<ElectricityPayment> getElectricityPaymentQueryWrapper(String search, String state){
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = new LambdaQueryWrapper<>();
        Integer data = null;
        if("未缴费".equals(state) || "已缴费".equals(state))  data = ("未缴费".equals(state) ? 0 : 1);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data)
                .and(i->i
                        .like(search != null, ElectricityPayment::getRoomId, search.trim())
                        .or()
                        .like(search != null, ElectricityPayment::getCampus, search.trim())
                        .or()
                        .like(search != null, ElectricityPayment::getBuilding, search.trim())
                        .or()
                        .like(search != null, ElectricityPayment::getArea, search.trim()));
        return queryWrapper;
    }

    public  LambdaQueryWrapper<ElectricityPayment> getElectricityPaymentQueryWrapper(String campus, String area, String building, String roomId, String state){
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = new LambdaQueryWrapper<>();
        Integer data = null;
        if("未缴费".equals(state) || "已缴费".equals(state))  data = ("未缴费".equals(state) ? 0 : 1);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data)
                .and(i->i.like(campus != null, ElectricityPayment::getCampus, campus.trim()))
                .and(i->i.like(area != null, ElectricityPayment::getArea, area.trim()))
                .and(i->i.like(building != null, ElectricityPayment::getBuilding, building.trim()))
                .and(i->i.like(roomId != null, ElectricityPayment::getRoomId, roomId.trim()));
        return queryWrapper;
    }

    public  LambdaQueryWrapper<ElectricityPayment> getElectricityPaymentQueryWrapper(Integer uid, String state){
        LambdaQueryWrapper<ElectricityPayment> queryWrapper = new LambdaQueryWrapper<>();
        Integer data = null;
        if("未缴费".equals(state) || "已缴费".equals(state))  data = ("未缴费".equals(state) ? 0 : 1);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data);
        queryWrapper.eq(data != null, ElectricityPayment::getStatus, data)
                .and(i->i.like(uid != null, ElectricityPayment::getUid, uid));
        return queryWrapper;
    }

}




