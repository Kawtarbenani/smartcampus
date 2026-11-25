package com.smartcampus.backend.dto;

import lombok.Data;

@Data
public class ModuleRequest {
    private String code;
    private String nom;
    private Integer credits;
    private Integer volumeHoraire;
    private Long filiereId;
    private Long semestreId;
}