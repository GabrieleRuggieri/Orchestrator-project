package com.gabriele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.gabriele.enums.ProcessStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "orchestratorProcess")
public class OrchestratorProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "status")
    private ProcessStatus status;

    @OneToMany(mappedBy = "orchestratorProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrchestratorProcessStep> orchestratorProcessSteps;

}