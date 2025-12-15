package com.hby.coachfit.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "exercises",
        uniqueConstraints = @UniqueConstraint(columnNames = {"coach_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // coach peut être null si tu veux des exos "globaux" plus tard
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "primary_muscle", nullable = false, length = 50)
    private String primaryMuscle;

    @Column(name = "equipment", length = 50)
    private String equipment;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
    }
}
