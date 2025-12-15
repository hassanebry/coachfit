package com.hby.coachfit.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coach_clients",
        uniqueConstraints = @UniqueConstraint(columnNames = {"coach_id", "client_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coach_id", nullable = false)
    private User coach;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

}