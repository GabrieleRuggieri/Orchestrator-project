package com.gabriele.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gabriele.common.dto.OrderRequestDTO;
import com.gabriele.common.dto.OrderResponseDTO;
import com.gabriele.entity.Order;
import com.gabriele.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public Mono<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }



    @GetMapping("/all")
    public Flux<OrderResponseDTO> getAllOrders() {
        return this.orderService.getAllOrders();
    }

}
