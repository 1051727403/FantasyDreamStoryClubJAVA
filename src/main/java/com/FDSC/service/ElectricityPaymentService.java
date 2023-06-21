package com.FDSC.service;

import com.FDSC.entity.ElectricityPayment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.FDSC.controller.dto.ElectricityPaymentDto;

import java.util.List;

/**
* @author maple
* @description 针对表【sys_electricity_payment】的数据库操作Service
* @createDate 2023-02-13 13:16:02
*/
public interface ElectricityPaymentService extends IService<ElectricityPayment> {
    public List<ElectricityPaymentDto> getAllInfo();

    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, String search, String state);
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, String search, String state);

    // 获取根据模糊查询和当前缴费信息的所有数据
    public List<ElectricityPayment> getElectricityPayment(String search, String state);

    // 根据位置信息查找缴费
    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, String campus, String area, String building, String roomId, String state);
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, String campus, String area, String building, String roomId, String state);
    // 根据uid查找缴费记录
    public Page<ElectricityPayment> getPageElectricityPayment(Integer pageNum, Integer pageSize, Integer uid, String state);
    public Page<ElectricityPaymentDto> getPageElectricityPaymentDto(Integer pageNum, Integer pageSize, Integer uid, String state);
}
