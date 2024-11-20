package com.gabriele.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriele.common.dto.OrchestratorRequestDTO;
import com.gabriele.common.dto.OrchestratorResponseDTO;
import com.gabriele.common.dto.OrderRequestDTO;
import com.gabriele.common.dto.OrderResponseDTO;
import com.gabriele.common.enums.OrderStatus;
import com.gabriele.entity.Order;
import com.gabriele.repository.OrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.*;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Sinks.Many<OrchestratorRequestDTO> sink;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<Order> createOrder(OrderRequestDTO orderRequestDTO) {
        return convertToEntity(orderRequestDTO)
                .flatMap(order -> orderRepository.save(order)
                        .flatMap(savedOrder -> getOrchestratorRequestDTO(orderRequestDTO, savedOrder.getUuidOrder())
                                .doOnNext(sink::tryEmitNext)
                                .thenReturn(savedOrder)))
                .doOnNext(savedOrder -> log.info("Order created with Pending Status: {}", savedOrder.getUuidOrder()));
    }

    public Flux<OrderResponseDTO> getAllOrders() {
        return this.orderRepository.findAll().map(this::convertToDTO);
    }

    private Mono<Order> convertToEntity(OrderRequestDTO dto) {
        return Mono.fromCallable(() -> modelMapper.map(dto, Order.class))
                .flatMap(order -> {
                    order.setUuidOrder(UUID.randomUUID().toString());

                    return inventoryService.getInventoryItem(order.getUuidItem())
                            .switchIfEmpty(Mono.error(new RuntimeException("Item not found")))
                            .map(item -> {
                                order.setPrice(item.getPrice());
                                order.setStatus(OrderStatus.PENDING);
                                order.setCreationDate(LocalDateTime.now());
                                return order;
                            });
                });
    }

    private OrderResponseDTO convertToDTO(Order order) {

        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

        dto.setAmount(order.getPrice());
        dto.setUuidOrder(order.getUuidOrder());

        return dto;
    }

    public Mono<OrchestratorRequestDTO> getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO, String uuidOrder) {
        return inventoryService.getInventoryItem(orderRequestDTO.getUuidItem())
                .switchIfEmpty(Mono.error(new RuntimeException("Item not found")))
                .map(item -> {
                    OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
                    requestDTO.setAmount(item.getPrice());
                    requestDTO.setUuidOrder(uuidOrder);
                    requestDTO.setUuidItem(orderRequestDTO.getUuidItem());
                    requestDTO.setUuidCustomer(orderRequestDTO.getUuidCustomer());
                    return requestDTO;
                });
    }

    public Mono<Void> updateOrder(OrchestratorResponseDTO responseDTO, String uuidOrder) {
        return this.orderRepository.findByUuidOrder(uuidOrder)
                .doOnNext(p -> p.setStatus(responseDTO.getStatus()))
                .flatMap(this.orderRepository::save)
                .doOnSuccess(s -> log.info("Order updated with Status: {} ", responseDTO.getStatus()))
                .then();
    }

}
