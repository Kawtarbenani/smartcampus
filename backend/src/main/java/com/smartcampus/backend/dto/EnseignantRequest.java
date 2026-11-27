package com.smartcampus.backend.dto;
import lombok.Data;
@Data
public class EnseignantRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String specialite;
    private String grade;
}