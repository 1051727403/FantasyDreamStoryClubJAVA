package com.FDSC.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor//自动创建无参构造
@AllArgsConstructor//自动创建有参构造
//mybatis-plus会直接去数据库中查找，名字必须一致或者用tablename
@TableName(value = "water_order")
@ToString
public class WaterOrder {

    @TableId(type = IdType.AUTO)
    @TableField(value = "order_id")
    private Integer orderId;

    @Alias("订水者用户名（学号）")
    @TableField(value = "order_username")
    private String orderUsername;

    @Alias("商品名称")
    @TableField(value = "order_commodity_name")
    private String orderCommodityName;

    @Alias("商品数量")
    @TableField(value = "order_commodity_number")
    private String orderCommodityNumber;

    @Alias("订单价格")
    @TableField(value = "order_price")
    private double orderPrice;

    /*地址    start*/
    @Alias("校区")
    @TableField(value = "order_campus")
    private String orderCampus;

    @Alias("片区")
    @TableField(value = "order_area")
    private String orderArea;

    @Alias("楼幢号")
    @TableField(value = "order_building")
    private String orderBuilding;

    @Alias("宿舍号")
    @TableField(value = "order_dormitory")
    private String orderDormitory;

    @Alias("订单状态")
    @TableField(value = "order_is_complete")
    private Integer orderIsComplete;


    @Alias("订水时间")
    @TableField(value = "order_buy_time")
    private String orderBuyTime;

    @Alias("创建时间(导入时去除该列)")
    @TableField(value = "order_create_time")
    private LocalDateTime orderCreateTime;

}
