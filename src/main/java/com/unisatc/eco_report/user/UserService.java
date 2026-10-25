
package com.unisatc.eco_report.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unisatc.eco_report.user.dto.CreateUserRequest;
import com.unisatc.eco_report.user.dto.UserResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email().trim())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um usuário cadastrado com este e-mail"
            );
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuário não encontrado"
            ));

        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserResponse::from)
            .toList();
    }
}
