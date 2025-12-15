package com.hby.coachfit.backend.domain;

import com.hby.coachfit.backend.domain.enums.ProgramStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "assigned_programs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"program_id", "client_id", "start_date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignedProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProgramStatus status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ProgramStatus.ACTIVE;
        }
    }
}
