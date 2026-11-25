package com.smartcampus.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "module")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(nullable = false)
    private Integer credits;

    @Column(name = "volume_horaire", nullable = false)
    private Integer volumeHoraire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;
}