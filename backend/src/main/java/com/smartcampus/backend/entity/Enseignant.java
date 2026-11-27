package com.smartcampus.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enseignant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enseignant {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false, length = 150)
    private String specialite;

    @Column(length = 100)
    private String grade;
}