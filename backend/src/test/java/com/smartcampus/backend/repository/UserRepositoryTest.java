package com.smartcampus.backend.repository;

import com.smartcampus.backend.Repository.UserRepository;
import com.smartcampus.backend.entity.Role;
import com.smartcampus.backend.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Devrait sauvegarder et retrouver un utilisateur par email")
    void testSaveAndFindByEmail() {
        // GIVEN (Données d'entrée)
        User user = User.builder()
                .nom("Benali")
                .prenom("Karim")
                .email("karim.benali@smartcampus.tn")
                .password("motdepasse123")
                .role(Role.ETUDIANT)
                .build();

        // WHEN (Action)
        userRepository.save(user);
        Optional<User> resultat = userRepository.findByEmail("karim.benali@smartcampus.tn");

        // THEN (Vérifications)
        assertTrue(resultat.isPresent());
        assertEquals("Benali", resultat.get().getNom());
        assertEquals(Role.ETUDIANT, resultat.get().getRole());
        assertTrue(userRepository.existsByEmail("karim.benali@smartcampus.tn"));
    }
}