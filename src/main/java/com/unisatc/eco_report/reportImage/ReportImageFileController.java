
package com.unisatc.eco_report.reportImage;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por disponibilizar os arquivos
 * das imagens armazenadas no servidor.
 */
@RestController
@RequestMapping("/api/report-images")
public class ReportImageFileController {

    private final ReportImageService reportImageService;

    public ReportImageFileController(ReportImageService reportImageService) {
        this.reportImageService = reportImageService;
    }

    
    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(
        @PathVariable Long imageId
    ) {
        Resource image = reportImageService.getImageFile(imageId);

        MediaType contentType = MediaTypeFactory.getMediaType(image)
            .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
            .contentType(contentType)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.inline()
                    .filename(image.getFilename())
                    .build()
                    .toString()
            )
            .body(image);
    }

}
