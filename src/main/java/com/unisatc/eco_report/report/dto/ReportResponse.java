package com.unisatc.eco_report.report.dto;

import java.time.LocalDateTime;

import com.unisatc.eco_report.report.Report;

/** DTO de resposta da API com os dados de uma ocorrência. */
public record ReportResponse(
    Long id,
    String title,
    String description,
    String location,
    String categoryName,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static ReportResponse from(Report report) {
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
