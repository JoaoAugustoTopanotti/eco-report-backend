
package com.unisatc.eco_report.category;

import org.springframework.stereotype.Component;

import com.unisatc.eco_report.category.dto.CategoryResponse;

/** Converte entidades Category em objetos de resposta da API. */
@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getStatus()
        );
    }
}
