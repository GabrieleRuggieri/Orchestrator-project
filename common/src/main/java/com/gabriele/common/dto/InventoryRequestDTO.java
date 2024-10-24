package com.gabriele.common.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {

    private String uuidCustomer;
    private String uuidItem;
    private String uuidOrder;

}
