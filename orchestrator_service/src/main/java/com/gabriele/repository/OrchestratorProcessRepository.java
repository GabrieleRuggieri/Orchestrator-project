package com.gabriele.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriele.entity.OrchestratorProcess;

import reactor.core.publisher.Mono;

@Repository
public interface OrchestratorProcessRepository extends ReactiveCrudRepository<OrchestratorProcess, Long> {

}
