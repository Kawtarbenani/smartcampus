package com.smartcampus.backend.controller;

import com.smartcampus.backend.dto.EtudiantRequest;
import com.smartcampus.backend.dto.EtudiantResponse;
import com.smartcampus.backend.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/etudiants")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EtudiantAdminController {

    private final EtudiantService etudiantService;

    @GetMapping
    public List<EtudiantResponse> getAll() {
        return etudiantService.getAllEtudiants();
    }

    @PostMapping
    public ResponseEntity<EtudiantResponse> create(@RequestBody EtudiantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.createEtudiant(request));
    }

    @PutMapping("/{id}")
    public EtudiantResponse update(@PathVariable Long id, @RequestBody EtudiantRequest request) {
        return etudiantService.updateEtudiant(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }
}