package com.smartcampus.backend.dto;

import com.smartcampus.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;

    @Builder.Default
    private String type = "Bearer";

    private String email;
    private Role role;
}