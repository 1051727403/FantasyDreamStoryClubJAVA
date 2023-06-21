package com.FDSC.mapper;
import com.FDSC.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommodityMapper extends BaseMapper<Commodity>{

    //返回所有商品
     @Select("select commodity_id,commodity_name,commodity_price,commodity_supplier,commodity_avatar,commodity_create_time from water_commodity where commodity_is_removed=0")
     List<Commodity> getList();
    
    //返回商品数量
    @Select("select count(*) from water_commodity where commodity_is_removed=0")
    Integer selectCount() ;

    //分页查询
    List<Commodity> changePage(Integer pageNum,Integer pageSize,String search);


    //删除商品，假删除，更改商品commodity_is_removed属性，0->1 1代表已下架
    @Update("UPDATE water_commodity SET commodity_is_removed=1 WHERE commodity_id=#{id}")
    boolean delCommodity(Integer id);


    //批量删除商品 delBashCommodity.xml
    boolean delBashCommodity(@Param("ids") List<Integer> ids);

    Integer changePageCommodityCount(Integer pageNum, Integer pageSize, String search);
}
