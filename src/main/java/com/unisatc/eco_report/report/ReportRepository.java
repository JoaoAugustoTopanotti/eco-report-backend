package com.unisatc.eco_report.report;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repositório JPA para acesso aos dados de ocorrências. */
public interface ReportRepository extends JpaRepository<Report, Long> {
}
