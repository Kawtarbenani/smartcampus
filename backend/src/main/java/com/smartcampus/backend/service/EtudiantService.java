package com.smartcampus.backend.service;

import com.smartcampus.backend.dto.EtudiantRequest;
import com.smartcampus.backend.dto.EtudiantResponse;
import com.smartcampus.backend.entity.Etudiant;
import com.smartcampus.backend.entity.Filiere;
import com.smartcampus.backend.entity.Role;
import com.smartcampus.backend.entity.User;
import com.smartcampus.backend.Repository.EtudiantRepository;
import com.smartcampus.backend.Repository.FiliereRepository;
import com.smartcampus.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final UserRepository userRepository;
    private final FiliereRepository filiereRepository;
    private final PasswordEncoder passwordEncoder;

    public List<EtudiantResponse> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EtudiantResponse createEtudiant(EtudiantRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        if (etudiantRepository.existsByMatricule(req.getMatricule())) {
            throw new IllegalArgumentException("Matricule déjà utilisé");
        }

        Filiere filiere = req.getFiliereId() != null
                ? filiereRepository.findById(req.getFiliereId())
                .orElseThrow(() -> new IllegalArgumentException("Filière introuvable"))
                : null;

        String rawPassword = (req.getPassword() != null && !req.getPassword().isBlank())
                ? req.getPassword() : "etudiant123";

        User user = User.builder()
                .nom(req.getNom())
                .prenom(req.getPrenom())
                .email(req.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ETUDIANT)
                .build();

        Etudiant etudiant = Etudiant.builder()
                .user(user)
                .matricule(req.getMatricule())
                .annee(req.getAnnee())
                .filiere(filiere)
                .build();

        return toResponse(etudiantRepository.save(etudiant));
    }

    @Transactional
    public EtudiantResponse updateEtudiant(Long id, EtudiantRequest req) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable"));

        User user = etudiant.getUser();
        user.setNom(req.getNom());
        user.setPrenom(req.getPrenom());
        user.setEmail(req.getEmail());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        etudiant.setMatricule(req.getMatricule());
        etudiant.setAnnee(req.getAnnee());

        if (req.getFiliereId() != null) {
            Filiere filiere = filiereRepository.findById(req.getFiliereId())
                    .orElseThrow(() -> new IllegalArgumentException("Filière introuvable"));
            etudiant.setFiliere(filiere);
        } else {
            etudiant.setFiliere(null);
        }

        return toResponse(etudiantRepository.save(etudiant));
    }

    @Transactional
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    private EtudiantResponse toResponse(Etudiant e) {
        return EtudiantResponse.builder()
                .id(e.getId())
                .nom(e.getUser().getNom())
                .prenom(e.getUser().getPrenom())
                .email(e.getUser().getEmail())
                .matricule(e.getMatricule())
                .annee(e.getAnnee())
                .filiereId(e.getFiliere() != null ? e.getFiliere().getId() : null)
                .filiereCode(e.getFiliere() != null ? e.getFiliere().getCode() : null)
                .filiereNom(e.getFiliere() != null ? e.getFiliere().getNom() : null)
                .build();
    }
}