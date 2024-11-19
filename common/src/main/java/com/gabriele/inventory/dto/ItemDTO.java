package com.gabriele.inventory.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ItemDTO {

    private String uuid;
    private String name;
    private BigDecimal price;
    private Integer stockAvailable;
    private Boolean state;
}
