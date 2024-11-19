package com.gabriele.common.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderRequestDTO {

	private String uuidCustomer;
    private String uuidItem;
}