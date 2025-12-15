package com.hby.coachfit.backend.domain;

import com.hby.coachfit.backend.domain.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "workout_sessions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assigned_program_id", "program_day_id", "session_date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assigned_program_id", nullable = false)
    private AssignedProgram assignedProgram;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_day_id", nullable = false)
    private ProgramDay programDay;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SessionStatus status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}
