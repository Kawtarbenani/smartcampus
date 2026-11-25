package com.smartcampus.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EtudiantResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String matricule;
    private Integer annee;
    private Long filiereId;
    private String filiereCode;
    private String filiereNom;
}