
package com.unisatc.eco_report.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReportRequest(
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 120, message = "O título deve ter entre 3 e 120 caracteres")
    String title,

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 2000, message = "A descrição deve ter entre 10 e 2000 caracteres")
    String description,

    @NotBlank(message = "A localização é obrigatória")
    @Size(min = 3, max = 255, message = "A localização deve ter entre 3 e 255 caracteres")
    String location,

    @NotNull(message = "A categoria é obrigatória")
    Long categoryId,

    @NotNull(message = "O usuário é obrigatório")
    Long userId
) {}
