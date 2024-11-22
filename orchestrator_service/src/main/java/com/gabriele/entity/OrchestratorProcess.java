package com.gabriele.entity;

import com.gabriele.enums.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table("ORCHESTRATOR_PROCESS")
public class OrchestratorProcess {

    @Id
    @Column("id")
    private Long id;

    @Column("uuid_orchestrator_process")
    private String uuidOrchestratorProcess;

    @Column("status")
    private ProcessStatus status;

}