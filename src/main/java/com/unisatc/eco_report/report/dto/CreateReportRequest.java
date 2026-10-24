package com.unisatc.eco_report.report.dto;

public record CreateReportRequest(
    String title,
    String description,
    String location,
    Long categoryId,
    Long userId 
) {}
