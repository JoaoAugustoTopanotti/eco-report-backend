
package com.unisatc.eco_report.user;

import org.springframework.stereotype.Component;

import com.unisatc.eco_report.user.dto.UserResponse;

/** Converte entidades User em objetos de resposta da API. */
@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt()
        );
    }
}
