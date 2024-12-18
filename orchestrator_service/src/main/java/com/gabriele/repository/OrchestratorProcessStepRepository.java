package com.gabriele.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriele.entity.OrchestratorProcessStep;

import reactor.core.publisher.Flux;

@Repository
public interface OrchestratorProcessStepRepository extends ReactiveCrudRepository<OrchestratorProcessStep, Long> {

}
