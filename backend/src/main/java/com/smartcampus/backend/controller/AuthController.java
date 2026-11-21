package com.smartcampus.backend.controller;

import com.smartcampus.backend.Repository.UserRepository;
import com.smartcampus.backend.dto.AuthResponse;
import com.smartcampus.backend.dto.LoginRequest;
import com.smartcampus.backend.entity.User;
import com.smartcampus.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permet à votre frontend (Vite/React) d'appeler l'API sans être bloqué par CORS
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // 1. Vérifier l'email et le mot de passe (si faux, Spring renvoie automatiquement 401 Unauthorized)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Récupérer l'utilisateur correspondant depuis la base
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 3. Générer le Token JWT signé
        String jwtToken = jwtService.generateToken(user);

        // 4. Construire et retourner la réponse au client
        AuthResponse response = AuthResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(response);
    }
}