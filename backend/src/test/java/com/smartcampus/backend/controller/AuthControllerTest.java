package com.smartcampus.backend.controller;

import com.smartcampus.backend.Repository.UserRepository;
import com.smartcampus.backend.dto.AuthResponse;
import com.smartcampus.backend.dto.LoginRequest;
import com.smartcampus.backend.entity.Role;
import com.smartcampus.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User testUser = User.builder()
                .nom("Benali")
                .prenom("Karim")
                .email("karim@smartcampus.tn")
                .password(passwordEncoder.encode("secret123"))
                .role(Role.ETUDIANT)
                .build();
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("Devrait se connecter avec succès et renvoyer un Token JWT")
    void testLoginSuccess() {
        // GIVEN (Identifiants valides)
        LoginRequest request = LoginRequest.builder()
                .email("karim@smartcampus.tn")
                .password("secret123")
                .build();

        // WHEN (Tentative de connexion)
        ResponseEntity<AuthResponse> response = authController.login(request);

        // THEN (Vérification du résultat et du Token JWT)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken(), "Le token JWT ne doit pas être null");
        assertEquals("Bearer", response.getBody().getType());
        assertEquals("karim@smartcampus.tn", response.getBody().getEmail());
        assertEquals(Role.ETUDIANT, response.getBody().getRole());
    }

    @Test
    @DisplayName("Devrait rejeter la connexion si le mot de passe est faux")
    void testLoginBadPassword() {
        // GIVEN (Mauvais mot de passe)
        LoginRequest badRequest = LoginRequest.builder()
                .email("karim@smartcampus.tn")
                .password("mauvaisMotDePasse")
                .build();

        // WHEN & THEN (Doit déclencher BadCredentialsException)
        assertThrows(BadCredentialsException.class, () -> authController.login(badRequest));
    }
}