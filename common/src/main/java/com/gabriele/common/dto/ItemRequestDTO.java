package com.gabriele.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequestDTO {

    private String name;
    private BigDecimal price;
    private Integer stockAvailable;
    private Boolean state;
}
