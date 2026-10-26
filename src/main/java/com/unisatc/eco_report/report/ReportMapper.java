
package com.unisatc.eco_report.report;

import org.springframework.stereotype.Component;

import com.unisatc.eco_report.report.dto.ReportResponse;

/** Converte entidades Report em objetos de resposta da API. */
@Component
public class ReportMapper {

    public ReportResponse toResponse(Report report) {
        return new ReportResponse(
            report.getId(),
            report.getTitle(),
            report.getDescription(),
            report.getLocation(),
            report.getCategory().getName(),
            report.getStatus(),
            report.getCreatedAt(),
            report.getUpdatedAt()
        );
    }
}
