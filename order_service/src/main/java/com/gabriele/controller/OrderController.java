package com.gabriele.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gabriele.common.dto.OrderRequestDTO;
import com.gabriele.common.dto.OrderResponseDTO;
import com.gabriele.entitty.Order;
import com.gabriele.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public Mono<Order> createOrder(OrderRequestDTO request) throws Exception{
    	return this.service.createOrder(request);
    }

    @GetMapping("/all")
    public Flux<OrderResponseDTO> getAllOrders(){
        return this.service.getAllOrders();
    }

}
