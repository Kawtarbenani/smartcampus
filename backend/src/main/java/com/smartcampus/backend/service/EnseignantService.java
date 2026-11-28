package com.smartcampus.backend.service;

import com.smartcampus.backend.Repository.AffectationRepository;
import com.smartcampus.backend.Repository.EnseignantRepository;
import com.smartcampus.backend.Repository.ModuleRepository;
import com.smartcampus.backend.Repository.UserRepository;
import com.smartcampus.backend.dto.*;
import com.smartcampus.backend.entity.*;
import com.smartcampus.backend.entity.Module;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final AffectationRepository affectationRepository;
    private final PasswordEncoder passwordEncoder;

    // --- ENSEIGNANTS ---
    public List<EnseignantResponse> getAllEnseignants() {
        return enseignantRepository.findAll().stream()
                .map(this::toEnseignantResponse)
                .toList();
    }

    @Transactional
    public EnseignantResponse createEnseignant(EnseignantRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        String rawPassword = (req.getPassword() != null && !req.getPassword().isBlank())
                ? req.getPassword() : "prof123";

        User user = User.builder()
                .nom(req.getNom())
                .prenom(req.getPrenom())
                .email(req.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ENSEIGNANT)
                .build();

        Enseignant enseignant = Enseignant.builder()
                .user(user)
                .specialite(req.getSpecialite())
                .grade(req.getGrade())
                .build();

        return toEnseignantResponse(enseignantRepository.save(enseignant));
    }

    @Transactional
    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }

    // --- AFFECTATIONS ---
    public List<AffectationResponse> getAllAffectations() {
        return affectationRepository.findAll().stream()
                .map(this::toAffectationResponse)
                .toList();
    }

    @Transactional
    public AffectationResponse affecterModule(AffectationRequest req) {
        String annee = (req.getAnneeUniversitaire() != null && !req.getAnneeUniversitaire().isBlank())
                ? req.getAnneeUniversitaire() : "2026-2027";

        if (affectationRepository.existsByEnseignantIdAndModuleIdAndAnneeUniversitaire(
                req.getEnseignantId(), req.getModuleId(), annee)) {
            throw new IllegalArgumentException("Ce module est déjà affecté à cet enseignant pour cette année");
        }

        Enseignant enseignant = enseignantRepository.findById(req.getEnseignantId())
                .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable"));
        Module module = moduleRepository.findById(req.getModuleId())
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable"));

        Affectation affectation = Affectation.builder()
                .enseignant(enseignant)
                .module(module)
                .anneeUniversitaire(annee)
                .build();

        return toAffectationResponse(affectationRepository.save(affectation));
    }

    @Transactional
    public void deleteAffectation(Long id) {
        affectationRepository.deleteById(id);
    }

    private EnseignantResponse toEnseignantResponse(Enseignant e) {
        return EnseignantResponse.builder()
                .id(e.getId())
                .nom(e.getUser().getNom())
                .prenom(e.getUser().getPrenom())
                .email(e.getUser().getEmail())
                .specialite(e.getSpecialite())
                .grade(e.getGrade())
                .build();
    }

    private AffectationResponse toAffectationResponse(Affectation a) {
        return AffectationResponse.builder()
                .id(a.getId())
                .enseignantId(a.getEnseignant().getId())
                .enseignantNomComplet(a.getEnseignant().getUser().getPrenom() + " " + a.getEnseignant().getUser().getNom())
                .moduleId(a.getModule().getId())
                .moduleCode(a.getModule().getCode())
                .moduleNom(a.getModule().getNom())
                .anneeUniversitaire(a.getAnneeUniversitaire())
                .build();
    }

    public List<EnseignantModuleResponse> getMesModules(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        return affectationRepository.findByEnseignantId(user.getId()).stream()
                .map(a -> EnseignantModuleResponse.builder()
                        .moduleId(a.getModule().getId())
                        .code(a.getModule().getCode())
                        .nom(a.getModule().getNom())
                        .credits(a.getModule().getCredits())
                        .volumeHoraire(a.getModule().getVolumeHoraire())
                        .filiereCode(a.getModule().getFiliere().getCode())
                        .filiereNom(a.getModule().getFiliere().getNom())
                        .semestreCode(a.getModule().getSemestre().getCode())
                        .anneeUniversitaire(a.getAnneeUniversitaire())
                        .build())
                .toList();
    }
}