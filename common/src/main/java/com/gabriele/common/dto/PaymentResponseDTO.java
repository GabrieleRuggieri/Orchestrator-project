package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import com.gabriele.common.enums.PaymentStatus;

@Data
public class PaymentResponseDTO {

    private String uuidCustomerAccount;
    private String uuidOrder;
    private BigDecimal amount;
    private PaymentStatus status;

}