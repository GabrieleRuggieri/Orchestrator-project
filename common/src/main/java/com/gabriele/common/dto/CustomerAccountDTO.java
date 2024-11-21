package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerAccountDTO {

    private String uuidCustomerAccount;
    private BigDecimal balance;
    private Boolean state;

}
