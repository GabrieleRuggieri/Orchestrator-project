package com.gabriele.eventhandler;

import com.gabriele.common.dto.OrchestratorRequestDTO;
import com.gabriele.common.dto.OrchestratorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabriele.service.OrderService;

import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class OrderListener {

    @Autowired
    private Flux<OrchestratorRequestDTO> flux;

//    @Autowired
//    OrderEventService orderEventService;

    @Autowired
    private OrderService orderService;

    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> supplier() {
        return () -> flux;
    }


    @Bean
    public Consumer<Flux<OrchestratorResponseDTO>> consumer() {
        return f -> f
                .doOnNext(m -> log.info("Consuming orchestrator event: {}", m))
                .flatMap(responseDTO -> {
                    String uuidOrder = responseDTO.getUuidOrder(); // Estratto dal messaggio
                    return this.orderService.updateOrder(responseDTO, uuidOrder);
                })
                .subscribe();
    }

//    @Bean
//    public Consumer<Flux<OrchestratorResponseDTO>> consumer() {
//        return f -> f
//                .doOnNext(m -> log.info("Consuming orchestrator event: {}", m))
//                .flatMap(responseDTO -> this.orderService.updateOrder(responseDTO))
//                .subscribe();
//    }

}
