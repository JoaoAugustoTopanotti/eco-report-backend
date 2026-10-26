
package com.unisatc.eco_report.user.dto;

import java.time.LocalDateTime;

import com.unisatc.eco_report.user.User;

/** DTO de resposta da API com os dados de um usuário. */
public record UserResponse(
    Long id,
    String name,
    String email,
    String role,
    LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt()
        );
    }
}
