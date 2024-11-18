package com.gabriele.service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.patroclos.common.dto.OrchestratorRequestDTO;
import com.patroclos.common.dto.OrchestratorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import com.gabriele.dto.OrchestratorProcessDTO;
import com.gabriele.entity.OrchestratorProcess;
import com.gabriele.entity.OrchestratorProcessStep;
import com.gabriele.enums.ProcessStatus;
import com.gabriele.orchestration.Process;
import com.gabriele.orchestration.steps.ProcessStep;
import com.gabriele.repository.OrchestratorProcessRepository;
import com.gabriele.repository.OrchestratorProcessStepRepository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrchestratorService {

    private static final BiFunction<Row, RowMetadata, OrchestratorProcessDTO> MAPPING_FUNCTION =
            (row, rowMetaData) ->
            {
                OrchestratorProcessDTO op = new OrchestratorProcessDTO();
                op.setUuid(row.get("uuid", String.class));
                op.setStatus(row.get("status", String.class));
                return op;
            };

    private static final BiFunction<Row, RowMetadata, OrchestratorProcessStep> MAPPING_FUNCTION_STEP =
            (row, rowMetaData) ->
            {
                OrchestratorProcessStep step = new OrchestratorProcessStep();
                step.setUuid(row.get("id", String.class));
                step.setOrchestratorProcessId(row.get("orchestratorProcess_id", Long.class));
                step.setName(row.get("name", String.class));
                step.setStatusStep(row.get("statusStep", Long.class));
                step.setStepType(row.get("stepType", String.class));
                step.setError(row.get("error", String.class));
                return step;
            };

    private static final String SQL =
            "SELECT * " +
                    " FROM orchestratorProcess " +
                    " INNER JOIN orchestratorProcessStep ops on ops.orchestratorProcess_id = orchestratorProcess.id";

    @Autowired
    private OrchestratorProcessRepository orchestratorProcessRepository;

    @Autowired
    private OrchestratorProcessStepRepository orchestratorProcessStepRepository;

    @Autowired
    private DatabaseClient databaseClient;

    public OrchestratorResponseDTO saveProcess(final Process process, OrchestratorRequestDTO requestDTO) {
        OrchestratorProcess persistProcess = new OrchestratorProcess();
        persistProcess.setStatus(process.status);

        orchestratorProcessRepository.save(persistProcess)
                .subscribe(s ->
                {
                    log.info("Process recorded with id: " + s.getId().toString());
                    insertSteps(persistProcess, process.steps);
                });

        if (process.status == ProcessStatus.FAILED) {
            insertSteps(persistProcess, process.rollbackSteps);
        }

        return process.getOnSuccessResponseDTO(requestDTO);
    }

    public void insertSteps(OrchestratorProcess persistProcess, List<ProcessStep> steps) {
        List<OrchestratorProcessStep> stepsPersist = steps.stream().map(s ->
        {
            OrchestratorProcessStep step = new OrchestratorProcessStep();
            step.setOrchestratorProcessId(persistProcess.getId());
            step.setName(s.getClass().getSimpleName());
            step.setStatusStep(Long.valueOf(s.getStatus().toString()));
            step.setError(s.getError());
            step.setStepType(s.getType().toString());
            return step;
        }).collect(Collectors.toList());

        Flux<OrchestratorProcessStep> persistedProcessSteps = orchestratorProcessStepRepository.saveAll(stepsPersist);
        persistedProcessSteps.subscribe(s ->
        {
            log.info("Process steps recorded with process id: " + s.getOrchestratorProcessId().toString());
        });
    }

    public Flux<OrchestratorProcessDTO> getOrchestratorProcesses() {
        return this.databaseClient
                .sql(SQL)
                .map(MAPPING_FUNCTION_STEP)
                .all()
                .groupBy(OrchestratorProcessStep::getOrchestratorProcessId)
                .sort(Comparator.comparing(GroupedFlux::key))
                .flatMap(group -> group
                        .collectList()
                        .map(grouplist ->
                        {
                            OrchestratorProcessDTO op = new OrchestratorProcessDTO();
                            op.setUuid(grouplist.get(0).getId().toString());
                            op.setOrchestratorProcessSteps(grouplist);
                            return op;
                        })
                );

    }

    public Mono<OrchestratorProcessDTO> getOrchestratorProcess(String uuid) {
        return this.databaseClient
                .sql(String.format("%s %s", SQL, " WHERE orchestratorProcess.id = :id"))
//                .bind("uuid", UUID.fromString(uuid))
                .bind("uuid", uuid)
                .map(MAPPING_FUNCTION)
                .first();
    }

}