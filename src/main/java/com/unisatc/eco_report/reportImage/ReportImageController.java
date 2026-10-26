
package com.unisatc.eco_report.reportImage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unisatc.eco_report.reportImage.dto.ReportImageResponse;
import com.unisatc.eco_report.reportImage.dto.UploadReportImageRequest;

import jakarta.validation.Valid;

/**
 * Controller responsável pelas rotas de gerenciamento
 * das imagens associadas às ocorrências ambientais.
 */
@RestController
@RequestMapping("/api/reports/{reportId}/images")
public class ReportImageController {

    private final ReportImageService reportImageService;

    public ReportImageController(ReportImageService reportImageService) {
        this.reportImageService = reportImageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ReportImageResponse upload(
        @PathVariable Long reportId,
        @Valid @ModelAttribute UploadReportImageRequest request
    ) {
        return reportImageService.upload(reportId, request.getFile());
    }

    
    @GetMapping
    public List<ReportImageResponse> listByReport(
        @PathVariable Long reportId
    ) {
        return reportImageService.listByReport(reportId);
    }
}

