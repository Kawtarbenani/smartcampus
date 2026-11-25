package com.smartcampus.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String nom;

    private String description;
}