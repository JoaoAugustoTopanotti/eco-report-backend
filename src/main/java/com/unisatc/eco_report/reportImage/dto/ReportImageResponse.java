
package com.unisatc.eco_report.reportImage.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa os dados de uma imagem retornados
 * pela API, sem expor o caminho físico do arquivo.
 */
public record ReportImageResponse(
    Long id,
    Long reportId,
    String imageUrl,
    LocalDateTime createdAt
) {
}
