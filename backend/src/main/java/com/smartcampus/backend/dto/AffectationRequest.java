package com.smartcampus.backend.dto;
import lombok.Data;
@Data
public class AffectationRequest {
    private Long enseignantId;
    private Long moduleId;
    private String anneeUniversitaire;
}