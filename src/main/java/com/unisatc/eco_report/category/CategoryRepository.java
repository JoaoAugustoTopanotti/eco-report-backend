package com.unisatc.eco_report.category;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repositório JPA para acesso aos dados de categorias. */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
