
package com.unisatc.eco_report.category.dto;

/** DTO de resposta da API com os dados de uma categoria. */
public record CategoryResponse(
    Long id,
    String name,
    String status
) {
}
