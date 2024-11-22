package com.gabriele.orchestration.steps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabriele.enums.ProcessStepType;

import reactor.core.publisher.Mono;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProcessStep {

    protected WebClient webClient;
    protected ProcessStepStatus status;
    protected ProcessStepType type;
    protected String error;

    //	public abstract ProcessStepStatus getStatus();
    //	public abstract ProcessStepType getType();
    //	public abstract void setType(ProcessStepType type);
    //	public abstract String getError();
    public abstract Mono<Boolean> process();

    public abstract Mono<Boolean> revert();

    public abstract ProcessStep copyStep();
}