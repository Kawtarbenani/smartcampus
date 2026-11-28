package com.smartcampus.backend.controller;

import com.smartcampus.backend.dto.EnseignantModuleResponse;
import com.smartcampus.backend.service.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enseignant")
@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;

    @GetMapping("/modules")
    public List<EnseignantModuleResponse> getMesModules(Authentication authentication) {
        return enseignantService.getMesModules(authentication.getName());
    }
}