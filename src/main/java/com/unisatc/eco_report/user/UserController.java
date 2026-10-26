
package com.unisatc.eco_report.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisatc.eco_report.user.dto.CreateUserRequest;
import com.unisatc.eco_report.user.dto.UserResponse;

import jakarta.validation.Valid;

/** Controlador REST que expõe os endpoints de usuários. */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
        @Valid @RequestBody CreateUserRequest request
    ) {
        UserResponse response = UserResponse.from(
            userService.create(request)
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
