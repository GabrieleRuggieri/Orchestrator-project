package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gabriele.common.enums.OrderStatus;

@Data
public class OrderResponseDTO {

    private String uuidOrder;
    private String uuidCustomerAccount;
    private String uuidItem;
    private BigDecimal amount;
    private OrderStatus status;
    private LocalDateTime created;

}
