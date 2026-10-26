package com.unisatc.eco_report.collectionpoint;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unisatc.eco_report.collectionpoint.dto.CollectionPointRequest;
import com.unisatc.eco_report.collectionpoint.dto.CollectionPointResponse;

@Service
public class CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final CollectionPointMapper collectionPointMapper;

    public CollectionPointService(
        CollectionPointRepository collectionPointRepository,
        CollectionPointMapper collectionPointMapper
    ) {
        this.collectionPointRepository = collectionPointRepository;
        this.collectionPointMapper = collectionPointMapper;
    }

    @Transactional(readOnly = true)
    public List<CollectionPointResponse> findAll() {
        return collectionPointRepository.findAll()
            .stream()
            .map(collectionPointMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CollectionPointResponse findById(Long id) {
        return collectionPointMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public CollectionPointResponse create(CollectionPointRequest request) {
        CollectionPoint collectionPoint = new CollectionPoint();
        applyRequest(collectionPoint, request);

        CollectionPoint savedCollectionPoint = collectionPointRepository.save(collectionPoint);

        return collectionPointMapper.toResponse(savedCollectionPoint);
    }

    @Transactional
    public CollectionPointResponse update(Long id, CollectionPointRequest request) {
        CollectionPoint collectionPoint = findEntityById(id);
        applyRequest(collectionPoint, request);

        CollectionPoint updatedCollectionPoint = collectionPointRepository.saveAndFlush(collectionPoint);

        return collectionPointMapper.toResponse(updatedCollectionPoint);
    }

    @Transactional
    public void delete(Long id) {
        CollectionPoint collectionPoint = findEntityById(id);

        collectionPointRepository.delete(collectionPoint);
    }

    private CollectionPoint findEntityById(Long id) {
        return collectionPointRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ecoponto não encontrado"
            ));
    }

    private void applyRequest(CollectionPoint collectionPoint, CollectionPointRequest request) {
        collectionPoint.setName(request.name());
        collectionPoint.setAddress(request.address());
        collectionPoint.setAcceptedMaterials(request.acceptedMaterials());
        collectionPoint.setOpeningHours(request.openingHours());
    }
}
