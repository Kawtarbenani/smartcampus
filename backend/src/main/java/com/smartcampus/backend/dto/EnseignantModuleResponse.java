package com.smartcampus.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnseignantModuleResponse {
    private Long moduleId;
    private String code;
    private String nom;
    private Integer credits;
    private Integer volumeHoraire;
    private String filiereCode;
    private String filiereNom;
    private String semestreCode;
    private String anneeUniversitaire;
}