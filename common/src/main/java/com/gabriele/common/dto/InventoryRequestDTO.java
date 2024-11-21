package com.gabriele.common.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {

    private String uuidCustomerAccount;
    private String uuidItem;
    private String uuidOrder;

}
