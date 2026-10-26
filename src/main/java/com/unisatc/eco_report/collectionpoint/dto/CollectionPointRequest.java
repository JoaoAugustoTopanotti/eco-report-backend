package com.unisatc.eco_report.collectionpoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CollectionPointRequest(
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    String name,

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    String address,

    @NotBlank(message = "Os materiais aceitos são obrigatórios")
    @Size(max = 255, message = "Os materiais aceitos devem ter no máximo 255 caracteres")
    String acceptedMaterials,

    @Size(max = 100, message = "O horário de funcionamento deve ter no máximo 100 caracteres")
    String openingHours
) {}
