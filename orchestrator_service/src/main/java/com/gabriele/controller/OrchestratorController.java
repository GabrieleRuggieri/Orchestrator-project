package com.gabriele.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriele.dto.OrchestratorProcessDTO;
import com.gabriele.service.OrchestratorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orchestrator")
public class OrchestratorController {

    @Autowired
    private OrchestratorService orchestratorService;

    @GetMapping("/processes")
    public Flux<OrchestratorProcessDTO> getAllOrchestratorProcesses() {
        return orchestratorService.getOrchestratorProcesses();
    }

    @GetMapping("/process/{uuid}")
    public Mono<OrchestratorProcessDTO> getOrchestratorProcess(@PathVariable String uuid) {
        return orchestratorService.getOrchestratorProcess(uuid);
    }

}
