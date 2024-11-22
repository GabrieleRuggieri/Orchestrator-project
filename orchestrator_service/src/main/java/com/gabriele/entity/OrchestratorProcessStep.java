package com.gabriele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table("ORCHESTRATOR_PROCESS_STEP")
public class OrchestratorProcessStep {

    @Id
    @Column("id")
    private Long id;

    @Column("uuid_orchestrator_process_step")
    private String uuidOrchestratorProcessStep;

    @Column("id_orchestrator_process")
    private Long orchestratorProcessId;

    @Column("step_type")
    private String stepType;

    @Column("name")
    private String name;

    @Column("error")
    private String error;

    @Column("status_step")
    private Long statusStep;
}