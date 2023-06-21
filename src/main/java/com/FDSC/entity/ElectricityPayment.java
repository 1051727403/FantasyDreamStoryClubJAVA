package com.FDSC.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName sys_electricity_payment
 */
@TableName(value ="sys_electricity_payment")
@Data
@ToString
public class ElectricityPayment implements Serializable {
    // 主键自增, 而不是用雪花算法
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private BigDecimal money;

    private Integer status;

    private String roomId;

    private String building;

    private String campus;

    private String area;

    @TableField(value = "createTime")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}
