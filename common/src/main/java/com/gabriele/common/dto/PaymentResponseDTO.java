package com.gabriele.common.dto;

import lombok.Data;

import java.util.UUID;

import com.gabriele.common.enums.PaymentStatus;

@Data
public class PaymentResponseDTO {
    private Integer customerId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}
