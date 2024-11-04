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
@Table(name = "orchestratorProcessStep")
public class OrchestratorProcessStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;

    @Column(name = "orchestratorProcess_id")
    private String orchestratorProcessId;
    @Column(name = "stepType")
    private String stepType;
    private String name;
    private String error;
    @Column(name = "statusStep")
    private String statusStep;
}
