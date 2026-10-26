package com.unisatc.eco_report.collectionpoint;

import org.springframework.stereotype.Component;

import com.unisatc.eco_report.collectionpoint.dto.CollectionPointResponse;

@Component
public class CollectionPointMapper {

    public CollectionPointResponse toResponse(CollectionPoint collectionPoint) {
        return new CollectionPointResponse(
            collectionPoint.getId(),
            collectionPoint.getName(),
            collectionPoint.getAddress(),
            collectionPoint.getAcceptedMaterials(),
            collectionPoint.getOpeningHours(),
            collectionPoint.getCreatedAt(),
            collectionPoint.getUpdatedAt()
        );
    }
}
