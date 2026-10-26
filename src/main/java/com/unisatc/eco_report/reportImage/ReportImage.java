
package com.unisatc.eco_report.reportImage;

import java.time.LocalDateTime;

import com.unisatc.eco_report.category.Category;
import com.unisatc.eco_report.report.Report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * Representa os metadados de uma imagem associada a uma ocorrência ambiental.
 * Armazena a referência da imagem e sua data de criação.
 */
@Entity
@Table(name = "report_images")
public class ReportImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected ReportImage() {
    }

    public static ReportImage create() {
        return new ReportImage();
    }

    /**
     * Preenche a data de criação antes de persistir a imagem,
     * caso ela ainda não tenha sido definida.
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public ReportImage(Report report, String imageUrl) {
    this.report = report;
    this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) { this.report = report; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
