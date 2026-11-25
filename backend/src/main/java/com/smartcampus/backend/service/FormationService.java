package com.smartcampus.backend.service;

import com.smartcampus.backend.dto.ModuleRequest;
import com.smartcampus.backend.entity.Filiere;
import com.smartcampus.backend.entity.Module;
import com.smartcampus.backend.entity.Semestre;
import com.smartcampus.backend.Repository.FiliereRepository;
import com.smartcampus.backend.Repository.ModuleRepository;
import com.smartcampus.backend.Repository.SemestreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormationService {

    private final FiliereRepository filiereRepository;
    private final SemestreRepository semestreRepository;
    private final ModuleRepository moduleRepository;

    // --- FILIÈRES ---
    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere createFiliere(Filiere filiere) {
        if (filiereRepository.existsByCode(filiere.getCode())) {
            throw new IllegalArgumentException("Code filière déjà utilisé");
        }
        return filiereRepository.save(filiere);
    }

    public Filiere updateFiliere(Long id, Filiere updated) {
        Filiere existing = filiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable"));
        existing.setCode(updated.getCode());
        existing.setNom(updated.getNom());
        existing.setDescription(updated.getDescription());
        return filiereRepository.save(existing);
    }

    public void deleteFiliere(Long id) {
        filiereRepository.deleteById(id);
    }

    // --- SEMESTRES ---
    public List<Semestre> getAllSemestres() {
        return semestreRepository.findAll();
    }

    public Semestre createSemestre(Semestre semestre) {
        if (semestreRepository.existsByCode(semestre.getCode())) {
            throw new IllegalArgumentException("Code semestre déjà utilisé");
        }
        return semestreRepository.save(semestre);
    }

    public void deleteSemestre(Long id) {
        semestreRepository.deleteById(id);
    }

    // --- MODULES ---
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module createModule(ModuleRequest req) {
        if (moduleRepository.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("Code module déjà utilisé");
        }
        Filiere filiere = filiereRepository.findById(req.getFiliereId())
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable"));
        Semestre semestre = semestreRepository.findById(req.getSemestreId())
                .orElseThrow(() -> new IllegalArgumentException("Semestre introuvable"));

        Module module = Module.builder()
                .code(req.getCode())
                .nom(req.getNom())
                .credits(req.getCredits())
                .volumeHoraire(req.getVolumeHoraire())
                .filiere(filiere)
                .semestre(semestre)
                .build();
        return moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
}