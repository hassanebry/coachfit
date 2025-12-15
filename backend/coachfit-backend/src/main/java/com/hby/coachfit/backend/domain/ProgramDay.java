package com.hby.coachfit.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "program_days",
        uniqueConstraints = @UniqueConstraint(columnNames = {"program_id", "day_index"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "day_index", nullable = false)
    private Integer dayIndex;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
