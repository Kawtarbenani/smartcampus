package com.smartcampus.backend.controller;

import com.smartcampus.backend.dto.*;
import com.smartcampus.backend.service.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class EnseignantAdminController {

    private final EnseignantService enseignantService;

    // --- ENSEIGNANTS ---
    @GetMapping("/enseignants")
    public List<EnseignantResponse> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }

    @PostMapping("/enseignants")
    public ResponseEntity<EnseignantResponse> createEnseignant(@RequestBody EnseignantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.createEnseignant(request));
    }

    @DeleteMapping("/enseignants/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    // --- AFFECTATIONS ---
    @GetMapping("/affectations")
    public List<AffectationResponse> getAllAffectations() {
        return enseignantService.getAllAffectations();
    }

    @PostMapping("/affectations")
    public ResponseEntity<AffectationResponse> affecterModule(@RequestBody AffectationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.affecterModule(request));
    }

    @DeleteMapping("/affectations/{id}")
    public ResponseEntity<Void> deleteAffectation(@PathVariable Long id) {
        enseignantService.deleteAffectation(id);
        return ResponseEntity.noContent().build();
    }
}