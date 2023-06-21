package com.FDSC.controller.dto;

import com.FDSC.entity.ElectricityPayment;
import com.FDSC.entity.User;
import lombok.Data;
import lombok.ToString;

/**
 * ClassName:ElectricityPaymentDto
 * Package: com.lms.logistics_management_system.controller.dto
 * Description:
 * Author maple
 * Create 2023-02-13
 * Version: v1.0
 */
@Data
@ToString
public class ElectricityPaymentDto extends ElectricityPayment {
    private User user;


}
