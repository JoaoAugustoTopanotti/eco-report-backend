
package com.unisatc.eco_report.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unisatc.eco_report.category.dto.CategoryResponse;

/** Contém as regras de negócio para criação e consulta de categorias. */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(
        CategoryRepository categoryRepository,
        CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::toResponse)
            .toList();
    }
}
