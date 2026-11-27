package com.smartcampus.backend.dto;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AffectationResponse {
    private Long id;
    private Long enseignantId;
    private String enseignantNomComplet;
    private Long moduleId;
    private String moduleCode;
    private String moduleNom;
    private String anneeUniversitaire;
}