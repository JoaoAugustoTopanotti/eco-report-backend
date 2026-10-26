
package com.unisatc.eco_report.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** DTO de requisição com os dados para criar uma categoria. */
public record CreateCategoryRequest(
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    String name
) {}
