package com.unisatc.eco_report.user;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repositório JPA para acesso aos dados de usuários. */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
}