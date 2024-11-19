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
        // Converte il DTO in entità (UUID generato automaticamente in convertToEntity)
        return Mono.fromCallable(() -> this.convertToEntity(orderRequestDTO))
                .flatMap(order -> this.orderRepository.save(order)) // Salva l'ordine
                .doOnNext(savedOrder -> {
                    // Emetti l'evento con il Sink
                    if (this.sink != null) {
                        OrchestratorRequestDTO orchestratorRequest = getOrchestratorRequestDTO(orderRequestDTO, savedOrder.getUuidOrder());
                        this.sink.tryEmitNext(orchestratorRequest);
                    }
                    log.info("Order created with Pending Status: {}", savedOrder.getUuidOrder());
                });
    }



    public Flux<OrderResponseDTO> getAllOrders() {
        return this.orderRepository.findAll().map(this::convertToDTO);
    }

    private Order convertToEntity(OrderRequestDTO dto) {
        Order order = modelMapper.map(dto, Order.class);

        // Genera l'UUID direttamente qui
        order.setUuidOrder(UUID.randomUUID().toString());

        inventoryService.getInventoryItem(order.getUuidItem())
                .map(i -> {
                    if (i == null) {
                        throw new RuntimeException("Item not found");
                    }
                    return i;
                })
                .doOnSuccess(c -> {
                    order.setPrice(c.getPrice());
                    order.setStatus(OrderStatus.PENDING);
                    order.setCreationDate(LocalDateTime.now());
                })
                .block(); // Sincronizza i dati con l'inventario

        return order;
    }



    private OrderResponseDTO convertToDTO(Order order) {

        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

        dto.setAmount(order.getPrice());
        dto.setUuidOrder(order.getUuidOrder());

        return dto;
    }


    public OrchestratorRequestDTO getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO, String uuidOrder) {

        OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
        requestDTO.setUuidCustomer(orderRequestDTO.getUuidCustomer());

        inventoryService.getInventoryItem(orderRequestDTO.getUuidItem())
                .map(i -> {
                    if (i == null) {
                        throw new RuntimeException("Item not found");
                    }
                    return i;
                })
                .doOnSuccess(c -> {
                    requestDTO.setAmount(c.getPrice());
                    requestDTO.setUuidOrder(uuidOrder);
                    requestDTO.setUuidItem(orderRequestDTO.getUuidItem());
                    requestDTO.setUuidCustomer(orderRequestDTO.getUuidCustomer());
                })
                .block();

        return requestDTO;
    }


    public Mono<Void> updateOrder(OrchestratorResponseDTO responseDTO, String uuidOrder) {
        return this.orderRepository.findByUuidOrder(uuidOrder)
                .doOnNext(p -> p.setStatus(responseDTO.getStatus()))
                .flatMap(this.orderRepository::save)
                .doOnSuccess(s -> log.info("Order updated with Status: {} ", responseDTO.getStatus()))
                .then();
    }

//    public Mono<Void> updateOrder(final OrchestratorResponseDTO responseDTO) {
//        return this.orderRepository.findByCustomerAndItem(responseDTO.getUuidCustomer(), responseDTO.getUuidItem())
//                .doOnNext(order -> order.setStatus(responseDTO.getStatus()))
//                .flatMap(this.orderRepository::save)
//                .doOnSuccess(order -> log.info("Order updated with Status: {}", responseDTO.getStatus()))
//                .then();
//    }


}
