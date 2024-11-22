package com.gabriele.dto;

import java.util.List;

import com.gabriele.entity.OrchestratorProcessStep;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrchestratorProcessDTO {

    private String uuidOrchestratorProcess;
    private String status;
    private List<OrchestratorProcessStep> orchestratorProcessStep;

}