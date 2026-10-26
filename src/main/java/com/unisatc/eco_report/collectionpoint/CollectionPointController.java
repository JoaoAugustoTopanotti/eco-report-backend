package com.unisatc.eco_report.collectionpoint;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisatc.eco_report.collectionpoint.dto.CollectionPointRequest;
import com.unisatc.eco_report.collectionpoint.dto.CollectionPointResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/collection-points")
public class CollectionPointController {

    private final CollectionPointService collectionPointService;

    public CollectionPointController(CollectionPointService collectionPointService) {
        this.collectionPointService = collectionPointService;
    }

    @GetMapping
    public List<CollectionPointResponse> findAll() {
        return collectionPointService.findAll();
    }

    @GetMapping("/{id}")
    public CollectionPointResponse findById(@PathVariable Long id) {
        return collectionPointService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CollectionPointResponse> create(
            @Valid @RequestBody CollectionPointRequest request
    ) {
        CollectionPointResponse response = collectionPointService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public CollectionPointResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CollectionPointRequest request
    ) {
        return collectionPointService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        collectionPointService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
