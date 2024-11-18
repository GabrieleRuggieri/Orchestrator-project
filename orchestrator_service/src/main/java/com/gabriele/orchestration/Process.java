package com.gabriele.orchestration;

import java.util.List;

import com.patroclos.common.dto.OrchestratorRequestDTO;
import com.patroclos.common.dto.OrchestratorResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabriele.configuration.GlobalModelMapper;
import com.gabriele.configuration.WebClientConfig;
import com.gabriele.enums.ProcessStatus;
import com.gabriele.orchestration.steps.ProcessStep;
import com.gabriele.service.OrchestratorService;

import reactor.core.publisher.Mono;

public abstract class Process {
	
	protected ModelMapper modelMapper = GlobalModelMapper.getModelMapper();

	protected OrchestratorService orchestratorService;
	protected WebClient paymentClient = WebClientConfig.paymentClient();
	protected WebClient inventoryClient =  WebClientConfig.inventoryClient();

	public ProcessStatus status = ProcessStatus.PENDING;
	public List<ProcessStep> steps = null;
	public List<ProcessStep> rollbackSteps = null;

	public abstract Mono<OrchestratorResponseDTO> revert(final Process process, final OrchestratorRequestDTO requestDTO);
	public abstract Mono<OrchestratorResponseDTO> process(OrchestratorRequestDTO requestDTO);
	public abstract OrchestratorResponseDTO getOnSuccessResponseDTO(OrchestratorRequestDTO requestDTO);
	public abstract OrchestratorResponseDTO getOnFailResponseDTO(OrchestratorRequestDTO requestDTO);

	public Process(OrchestratorService orchestratorService) {
		this.orchestratorService = orchestratorService;
	}
}