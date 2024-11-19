package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrchestratorRequestDTO {

    private String uuidCustomer;
    private String uuidItem;
    private String uuidOrder;
    private BigDecimal amount;

}
