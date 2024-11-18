package com.gabriele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "ORCHESTRATOR_PROCESS_STEP")
public class OrchestratorProcessStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "orchestratorProcess_id")
    private Long orchestratorProcessId;

    @Column(name = "stepType")
    private String stepType;

    @Column(name = "name")
    private String name;

    @Column(name = "error")
    private String error;

    @Column(name = "statusStep")
    private Long statusStep;

    @ManyToOne
    @JoinColumn(name = "id_orchestrator_process", nullable = false)
    private OrchestratorProcess orchestratorProcess;
}
