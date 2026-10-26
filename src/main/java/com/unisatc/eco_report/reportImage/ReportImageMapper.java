
package com.unisatc.eco_report.reportImage;

import org.springframework.stereotype.Component;

import com.unisatc.eco_report.reportImage.dto.ReportImageResponse;

/**
 * Converte entidades de imagens em DTOs de resposta da API.
 */
@Component
public class ReportImageMapper {

    public ReportImageResponse toResponse(ReportImage image) {
        return new ReportImageResponse(
            image.getId(),
            image.getReport().getId(),
            "/api/report-images/" + image.getId(),
            image.getCreatedAt()
        );
    }
}
