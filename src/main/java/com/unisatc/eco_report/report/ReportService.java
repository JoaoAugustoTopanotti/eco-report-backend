package com.unisatc.eco_report.report;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unisatc.eco_report.category.Category;
import com.unisatc.eco_report.category.CategoryRepository;
import com.unisatc.eco_report.report.dto.CreateReportRequest;
import com.unisatc.eco_report.report.dto.ReportResponse;
import com.unisatc.eco_report.user.User;
import com.unisatc.eco_report.user.UserRepository;

@Service 
public class ReportService {
    
    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ReportService(
        ReportRepository reportRepository,
        CategoryRepository categoryRepository,
        UserRepository userRepository
    ) {
        this.reportRepository = reportRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> findAll() {
        List<Report> reports = reportRepository.findAll();

    return reports.stream()
            .filter(report -> !report.isDeleted())
            .map(ReportResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public ReportResponse findById(Long id) {
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Denúncia não encontrada"
            ));

        return ReportResponse.from(report);
    }

    @Transactional
    public ReportResponse create(CreateReportRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Categoria não encontrada"
            ));

        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Usuário não encontrado"
            ));

        Report report = new Report();
        report.setTitle(request.title());
        report.setDescription(request.description());
        report.setLocation(request.location());
        report.setCategory(category);
        report.setUser(user);

        Report savedReport = reportRepository.save(report);

        return ReportResponse.from(savedReport);
    }

    private static final java.util.Set<String> VALID_STATUSES =
        java.util.Set.of("PENDING", "IN_ANALYSIS", "RESOLVED");

    @Transactional
    public Report updateStatus(Long id, String status) {
        if (status == null || !VALID_STATUSES.contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid report status"
            );
        }

        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Report not found"
                ));

        if (report.isDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Report not found"
            );
        }

        report.setStatus(status);

        return reportRepository.save(report);
    }

    @Transactional
    public void delete(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Report not found"
                ));

        if (report.isDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Report not found"
            );
        }

        report.setDeleted(true);

        reportRepository.save(report);
    }
}
