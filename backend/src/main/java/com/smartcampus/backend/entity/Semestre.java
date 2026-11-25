package com.smartcampus.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "semestre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code; // ex: S7

    @Column(nullable = false, length = 100)
    private String libelle; // ex: Semestre 7

    @Column(name = "annee_universitaire", nullable = false, length = 20)
    private String anneeUniversitaire; // ex: 2026-2027
}