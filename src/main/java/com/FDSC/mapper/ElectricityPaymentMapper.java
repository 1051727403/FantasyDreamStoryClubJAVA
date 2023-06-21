package com.FDSC.mapper;

import com.FDSC.entity.ElectricityPayment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.FDSC.controller.dto.ElectricityPaymentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author maple
* @description 针对表【sys_electricity_payment】的数据库操作Mapper
* @createDate 2023-02-13 13:16:02
* @Entity com.lms.logistics_management_system.entity.ElectricityPayment
*/
@Mapper
public interface ElectricityPaymentMapper extends BaseMapper<ElectricityPayment> {
    List<ElectricityPaymentDto> getAllInfo();
}




