
package com.unisatc.eco_report.reportImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA responsável pelo acesso aos metadados
 * das imagens associadas às ocorrências ambientais.
 */
public interface ReportImageRepository extends JpaRepository<ReportImage, Long> {

    List<ReportImage> findAllByReport_IdOrderByCreatedAtAsc(Long reportId);
}
