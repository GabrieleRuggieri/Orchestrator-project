package com.gabriele.inventory.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ItemDTO {

    private Long id;
    private String itemId;
    private BigDecimal price;
    private Integer stockAvailable;
}
