package com.unisatc.eco_report.collectionpoint.dto;

import java.time.LocalDateTime;

public record CollectionPointResponse(
    Long id,
    String name,
    String address,
    String acceptedMaterials,
    String openingHours,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
