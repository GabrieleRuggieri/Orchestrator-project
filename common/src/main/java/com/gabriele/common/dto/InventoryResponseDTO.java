package com.gabriele.common.dto;

import lombok.Data;

import com.gabriele.common.enums.InventoryStatus;

@Data
public class InventoryResponseDTO {

    private String uuidOrder;
    private String uuidCustomer;
    private String uuidItem;
    private InventoryStatus status;

}
