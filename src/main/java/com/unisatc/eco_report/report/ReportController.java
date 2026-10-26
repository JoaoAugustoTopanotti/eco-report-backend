
package com.unisatc.eco_report.report;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unisatc.eco_report.report.dto.CreateReportRequest;
import com.unisatc.eco_report.report.dto.ReportResponse;
import com.unisatc.eco_report.report.dto.UpdateReportStatus;
import jakarta.validation.Valid;

/** Controlador REST que expõe os endpoints de ocorrências. */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportResponse> findAll() {
        return reportService.findAll();
    }

    @GetMapping("/{id}")
    public ReportResponse findById(@PathVariable Long id) {
        return reportService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ReportResponse> create(
            @Valid @RequestBody CreateReportRequest request
    ) {
        ReportResponse response = reportService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Report> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateReportStatus dto
    ) {
        Report updatedReport =
                reportService.updateStatus(id, dto.status());

        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
