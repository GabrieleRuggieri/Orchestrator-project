package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerAccountRequestDTO {

    private BigDecimal balance;
    private Boolean state;

}
