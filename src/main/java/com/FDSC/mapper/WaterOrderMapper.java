package com.FDSC.mapper;

import com.FDSC.entity.WaterOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaterOrderMapper extends BaseMapper<WaterOrder> {


    //分页查询
    List<WaterOrder> changePage(Integer pageNum, Integer pageSize, String search,Integer selectCode,String today,String username);


    @Select("select count(*) from water_order")
    Integer selectCount();

    @Insert("INSERT INTO water_order(order_commodity_name,order_username,order_commodity_number,order_is_complete,order_buy_time,order_price," +
            "order_campus,order_area,order_building,order_dormitory)" +
            " VALUES(#{namestr},#{username},#{numstr},#{iscomplete},#{buytime},#{sumprice},#{campus},#{area},#{building},#{dormitory})")
    boolean upWaterOrder(String namestr,String username,String numstr,Integer iscomplete,String buytime,
                         Double sumprice,String campus,String area,String building,String dormitory);

    @Select("SELECT order_username,order_commodity_name,order_commodity_number,order_price,order_is_complete,order_buy_time,order_campus,order_area,order_building,order_dormitory FROM water_order")
    List<WaterOrder> getList();

    boolean changeStatusBash(List<Integer> ids);

    Integer changePageCount(Integer pageNum, Integer pageSize, String search, Integer selectCode, String today,String username);
}
