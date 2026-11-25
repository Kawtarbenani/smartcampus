package com.smartcampus.backend.dto;

import lombok.Data;

@Data
public class EtudiantRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password; // utilisé à la création (optionnel en modification)
    private String matricule;
    private Integer annee;
    private Long filiereId;
}