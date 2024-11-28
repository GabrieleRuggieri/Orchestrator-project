package com.gabriele.orchestration.steps;

import com.gabriele.common.dto.InventoryRequestDTO;
import com.gabriele.common.dto.InventoryResponseDTO;
import com.gabriele.common.enums.InventoryStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabriele.enums.ProcessStepType;

import reactor.core.publisher.Mono;

@Slf4j
public class InventoryStep extends ProcessStep {

    private final InventoryRequestDTO requestDTO;

    public InventoryStep(WebClient webClient, InventoryRequestDTO requestDTO, ProcessStepType type) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
        this.type = type;
    	this.status = ProcessStepStatus.PENDING;
    }
    
    public InventoryStep copyStep() {
       return new InventoryStep(this.webClient, this.requestDTO, this.type);
    }

    @Override
    public Mono<Boolean> process() {
    	log.info("Calling API {/stock/deduct}");
    	return webClient
                .post()
                .uri("/stock/deduct")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(InventoryResponseDTO.class)
                .doOnError(throwable -> 
                {
                	this.error = throwable.getMessage();
                	this.status = ProcessStepStatus.FAILED;
                })
                .map(r -> r.getStatus().equals(InventoryStatus.INSTOCK))
                .doOnSuccess(b -> this.status = b ? ProcessStepStatus.COMPLETE : ProcessStepStatus.FAILED)
                .doOnNext(b -> this.status = b ? ProcessStepStatus.COMPLETE : ProcessStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
    	log.info("Calling API {/stock/add}");
        return webClient
                    .post()
                    .uri("/stock/add")
                    .body(BodyInserters.fromValue(this.requestDTO))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .doOnError(throwable -> 
                    {
                    	this.error = throwable.getMessage();
                    	this.status = ProcessStepStatus.FAILED;
                    })
                    .map(r ->true)
                    .doOnSuccess(b -> this.setStatus(ProcessStepStatus.COMPLETE))
                    .onErrorReturn(false);
    }
}