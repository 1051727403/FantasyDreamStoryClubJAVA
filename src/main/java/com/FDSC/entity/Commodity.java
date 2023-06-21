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
@TableName(value = "water_commodity")
@ToString
public class Commodity {

    @TableId(type = IdType.AUTO)
    @TableField(value = "commodity_id")
    private Integer commodityId;

    @Alias("商品名称")
    @TableField(value = "commodity_name")
    private String commodityName;

    @Alias("商品价格")
    @TableField(value = "commodity_price")
    private Double commodityPrice;

    @Alias("商品供货商名称")
    @TableField(value = "commodity_supplier")
    private String commoditySupplier;

    @Alias("商品图片")
    @TableField(value = "commodity_avatar")
    private String commodityAvatar;

    @Alias("创建时间")
    @TableField(value = "commodity_create_time")
    private LocalDateTime commodityCreateTime;

}
