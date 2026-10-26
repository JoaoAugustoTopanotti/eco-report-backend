
package com.unisatc.eco_report.reportImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.unisatc.eco_report.report.Report;
import com.unisatc.eco_report.report.ReportRepository;
import com.unisatc.eco_report.reportImage.dto.ReportImageResponse;

@Service
public class ReportImageService {

    private final ReportImageRepository reportImageRepository;
    private final ReportRepository reportRepository;
    private final ReportImageMapper reportImageMapper;
    private final Path uploadDirectory;

    private static final Map<String, String> ALLOWED_TYPES = Map.of(
        "image/jpeg", ".jpg",
        "image/png", ".png",
        "image/webp", ".webp"
    );

    public ReportImageService(
        ReportImageRepository reportImageRepository,
        ReportRepository reportRepository,
        ReportImageMapper reportImageMapper,
        @Value("${eco-report.upload-dir:uploads}") String uploadDirectory
    ) {
        this.reportImageRepository = reportImageRepository;
        this.reportRepository = reportRepository;
        this.reportImageMapper = reportImageMapper;
        this.uploadDirectory = Path.of(uploadDirectory)
            .toAbsolutePath()
            .normalize();
    }

    @Transactional
    public ReportImageResponse upload(Long reportId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Selecione uma imagem."
            );
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "A imagem não pode ultrapassar 5 MB."
            );
        }

        String contentType = file.getContentType();
        String extension = ALLOWED_TYPES.get(contentType);

        if (extension == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Formato inválido. Envie uma imagem JPEG, PNG ou WebP."
            );
        }

        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ocorrência não encontrada."
            ));

        if (report.isDeleted()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possível adicionar imagens a uma ocorrência excluída."
            );
        }

        String fileName = UUID.randomUUID() + extension;
        Path destination = uploadDirectory.resolve(fileName).normalize();

        if (!destination.startsWith(uploadDirectory)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Caminho de arquivo inválido."
            );
        }

        try {
            Files.createDirectories(uploadDirectory);

            Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
            );

            ReportImage image = new ReportImage(report, fileName);
            report.addImage(image);
            
            ReportImage savedImage = reportImageRepository.save(image);

            return reportImageMapper.toResponse(savedImage);

        } catch (IOException exception) {
            try {
                Files.deleteIfExists(destination);
            } catch (IOException ignored) {
                // Mantém a exceção original.
            }

            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível salvar a imagem."
            );
        }
    }

    
    @Transactional(readOnly = true)
    public List<ReportImageResponse> listByReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ocorrência não encontrada."
            ));

        if (report.isDeleted()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ocorrência não encontrada."
            );
        }

        return reportImageRepository
            .findAllByReport_IdOrderByCreatedAtAsc(reportId)
            .stream()
            .map(reportImageMapper::toResponse)
            .toList();
    }

    
    @Transactional(readOnly = true)
    public Resource getImageFile(Long imageId) {
        ReportImage image = reportImageRepository.findById(imageId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Imagem não encontrada."
            ));

        Path filePath = uploadDirectory
            .resolve(image.getImageUrl())
            .normalize();

        if (!filePath.startsWith(uploadDirectory)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Imagem não encontrada."
            );
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arquivo da imagem não encontrado no armazenamento."
                );
            }

            return resource;
        } catch (java.net.MalformedURLException exception) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível acessar o arquivo da imagem."
            );
        }
    }

}
