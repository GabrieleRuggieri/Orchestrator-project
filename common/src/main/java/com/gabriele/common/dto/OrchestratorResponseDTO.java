package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import com.gabriele.common.enums.OrderStatus;

@Data
public class OrchestratorResponseDTO {

    private String uuidCustomerAccount;
    private String uuidOrder;
    private String uuidItem;
    private BigDecimal amount;
    private OrderStatus status;

}
