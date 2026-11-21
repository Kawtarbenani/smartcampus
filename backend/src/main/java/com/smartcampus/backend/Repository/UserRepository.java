package com.smartcampus.backend.Repository;

import com.smartcampus.backend.entity.Role;
import com.smartcampus.backend.entity.User;
import com.smartcampus.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Recherche par email (indispensable pour la connexion / Spring Security)
    Optional<User> findByEmail(String email);

    // 2. Vérification si un email existe déjà (indispensable pour l'inscription)
    boolean existsByEmail(String email);

    // 3. Récupérer tous les utilisateurs ayant un rôle spécifique (ex: tous les étudiants)
    List<User> findByRole(Role role);
}