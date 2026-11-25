package com.smartcampus.backend.controller;

import com.smartcampus.backend.dto.ModuleRequest;
import com.smartcampus.backend.entity.Filiere;
import com.smartcampus.backend.entity.Module;
import com.smartcampus.backend.entity.Semestre;
import com.smartcampus.backend.service.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FormationAdminController {

    private final FormationService formationService;

    // --- FILIÈRES ---
    @GetMapping("/filieres")
    public List<Filiere> getFilieres() {
        return formationService.getAllFilieres();
    }

    @PostMapping("/filieres")
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formationService.createFiliere(filiere));
    }

    @PutMapping("/filieres/{id}")
    public Filiere updateFiliere(@PathVariable Long id, @RequestBody Filiere filiere) {
        return formationService.updateFiliere(id, filiere);
    }

    @DeleteMapping("/filieres/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        formationService.deleteFiliere(id);
        return ResponseEntity.noContent().build();
    }

    // --- SEMESTRES ---
    @GetMapping("/semestres")
    public List<Semestre> getSemestres() {
        return formationService.getAllSemestres();
    }

    @PostMapping("/semestres")
    public ResponseEntity<Semestre> createSemestre(@RequestBody Semestre semestre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formationService.createSemestre(semestre));
    }

    @DeleteMapping("/semestres/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        formationService.deleteSemestre(id);
        return ResponseEntity.noContent().build();
    }

    // --- MODULES ---
    @GetMapping("/modules")
    public List<Module> getModules() {
        return formationService.getAllModules();
    }

    @PostMapping("/modules")
    public ResponseEntity<Module> createModule(@RequestBody ModuleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formationService.createModule(request));
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        formationService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}