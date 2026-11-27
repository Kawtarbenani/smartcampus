package com.smartcampus.backend.dto;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class EnseignantResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private String grade;
}