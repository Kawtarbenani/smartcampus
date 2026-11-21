package com.smartcampus.backend.config;

import com.smartcampus.backend.Repository.UserRepository;
import com.smartcampus.backend.entity.Role;
import com.smartcampus.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initTestUser() {
        return args -> {
            // Insère l'utilisateur test uniquement s'il n'existe pas déjà
            if (!userRepository.existsByEmail("admin@smartcampus.tn")) {
                User admin = User.builder()
                        .nom("Admin")
                        .prenom("System")
                        .email("admin@smartcampus.tn")
                        // TRÈS IMPORTANT : le mot de passe doit être hashé avec passwordEncoder
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
                System.out.println(">>> [SMARTCAMPUS] Utilisateur de test créé : admin@smartcampus.tn / admin123");
            }
        };
    }
}