package com.gabriele.entity;

import com.gabriele.common.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;

import java.util.UUID;

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