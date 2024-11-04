package com.gabriele.entity;

import lombok.Data;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;

import java.util.UUID;

import com.gabriele.common.enums.OrderStatus;

@Data
@ToString
public class PurchaseOrder {

    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;

}