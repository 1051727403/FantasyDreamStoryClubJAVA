package com.FDSC.controller.dto;

import com.FDSC.entity.ElectricityPayment;
import lombok.Data;

/**
 * ClassName:ElectricityPaymentUsernameDto
 * Package: com.lms.logistics_management_system.controller.dto
 * Description:
 * Author maple
 * Create 2023-02-21
 * Version: v1.0
 */
@Data
public class ElectricityPaymentUsernameDto extends ElectricityPayment {
    String username;
    String nickname;
}
