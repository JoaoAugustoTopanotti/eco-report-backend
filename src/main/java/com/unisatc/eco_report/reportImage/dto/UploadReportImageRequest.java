
package com.unisatc.eco_report.reportImage.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa os dados necessários para enviar
 * uma imagem associada a uma ocorrência ambiental.
 */
public class UploadReportImageRequest {

    @NotNull(message = "É necessário selecionar uma imagem.")
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
