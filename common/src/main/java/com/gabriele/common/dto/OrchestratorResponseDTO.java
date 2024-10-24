package com.gabriele.common.dto;

import lombok.Data;
import java.util.UUID;

import com.gabriele.common.enums.OrderStatus;

@Data
public class OrchestratorResponseDTO {

    private Integer customerId;
    private UUID orderId;
    private Double amount;
    private OrderStatus status;

}
