package com.hby.coachfit.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "program_day_exercises",
        uniqueConstraints = @UniqueConstraint(columnNames = {"program_day_id", "order_index"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramDayExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_day_id", nullable = false)
    private ProgramDay programDay;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "sets", nullable = false)
    private Integer sets;

    @Column(name = "reps_min")
    private Integer repsMin;

    @Column(name = "reps_max")
    private Integer repsMax;

    @Column(name = "target_rpe_min", precision = 3, scale = 1)
    private BigDecimal targetRpeMin;

    @Column(name = "target_rpe_max", precision = 3, scale = 1)
    private BigDecimal targetRpeMax;

    @Column(name = "rest_seconds")
    private Integer restSeconds;

    @Column(name = "notes")
    private String notes;
}
