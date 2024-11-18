package com.gabriele.orchestration;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.patroclos.common.dto.InventoryRequestDTO;
import com.patroclos.common.dto.OrchestratorRequestDTO;
import com.patroclos.common.dto.OrchestratorResponseDTO;
import com.patroclos.common.dto.PaymentRequestDTO;
import com.patroclos.common.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import com.gabriele.enums.ProcessStatus;
import com.gabriele.enums.ProcessStepType;
import com.gabriele.orchestration.steps.InventoryStep;
import com.gabriele.orchestration.steps.PaymentStep;
import com.gabriele.orchestration.steps.ProcessStep;
import com.gabriele.orchestration.steps.ProcessStepStatus;
import com.gabriele.service.OrchestratorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OrderProcess extends Process {

    public OrderProcess(OrchestratorService service, PaymentRequestDTO paymentRequest, InventoryRequestDTO inventoryRequestDTO) {
        super(service);
        ProcessStep paymentStep = new PaymentStep(this.paymentClient, paymentRequest, ProcessStepType.Process);
        ProcessStep stockAdjustStep = new InventoryStep(this.inventoryClient, inventoryRequestDTO, ProcessStepType.Process);
        this.steps = new LinkedList<ProcessStep>(List.of(paymentStep, stockAdjustStep));
    }

    @Override
    public Mono<OrchestratorResponseDTO> process(OrchestratorRequestDTO requestDTO) {
        return Flux.fromStream(() -> this.steps.stream())
                .map(s ->
                {
                    return s.process().block(); // wait for each step to finish before proceeding to the next
                })
                .handle(((Boolean, synchronousSink) -> {
                    if (Boolean) {
                        synchronousSink.next(true);
                    } else {
                        String error = "Process Failed";
                        synchronousSink.error(new Exception(error));
                    }
                }))
                .then(Mono.fromCallable(() ->
                {
                    this.status = ProcessStatus.COMPLETED;
                    return orchestratorService.saveProcess(this, requestDTO); //success
                }))
                .onErrorResume(ex ->
                {
                    ex.printStackTrace();
                    log.info("Process Rollback");
                    this.status = ProcessStatus.FAILED;
                    this.rollbackSteps = new LinkedList<ProcessStep>(this.steps);
                    this.rollbackSteps = this.rollbackSteps.stream()
                            .filter(s -> s.getStatus().equals(ProcessStepStatus.COMPLETE))
                            .map(s -> s.copyStep())
                            .collect(Collectors.toList());

                    this.rollbackSteps.forEach(s -> s.setType(ProcessStepType.RollBack));

                    var result = revert(this, requestDTO);
                    return result;
                });
    }

    @Override
    public Mono<OrchestratorResponseDTO> revert(final Process process, final OrchestratorRequestDTO requestDTO) {
        return Flux.fromStream(() -> process.rollbackSteps.stream())
                .flatMap(ProcessStep::revert)
                .retry(3)
                .then(Mono.fromCallable(() ->
                {
                    return orchestratorService.saveProcess(this, requestDTO); //success
                }))
                .then(Mono.just(getOnFailResponseDTO(requestDTO)))
                .doOnSuccess(s -> log.info("Saga Rollback Process Complete"));
    }

    public OrchestratorResponseDTO getOnSuccessResponseDTO(OrchestratorRequestDTO requestDTO) {
        OrchestratorResponseDTO responseDTO = modelMapper.map(requestDTO, OrchestratorResponseDTO.class);
        responseDTO.setStatus(OrderStatus.CREATED);
        return responseDTO;
    }

    public OrchestratorResponseDTO getOnFailResponseDTO(OrchestratorRequestDTO requestDTO) {
        OrchestratorResponseDTO responseDTO = modelMapper.map(requestDTO, OrchestratorResponseDTO.class);
        responseDTO.setStatus(OrderStatus.CANCELLED);
        return responseDTO;
    }
}