package com.unisatc.eco_report.collectionpoint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.unisatc.eco_report.collectionpoint.dto.CollectionPointRequest;
import com.unisatc.eco_report.collectionpoint.dto.CollectionPointResponse;

@WebMvcTest(CollectionPointController.class)
class CollectionPointControllerTest {

    private static final String VALID_BODY = """
        {
          "name": "Ecoponto Centro",
          "address": "Rua das Flores, 100",
          "acceptedMaterials": "Vidro, papel, eletrônicos",
          "openingHours": "Seg a Sex, 8h às 17h"
        }
        """;

    private static final CollectionPointResponse RESPONSE = new CollectionPointResponse(
        1L,
        "Ecoponto Centro",
        "Rua das Flores, 100",
        "Vidro, papel, eletrônicos",
        "Seg a Sex, 8h às 17h",
        LocalDateTime.of(2026, 10, 26, 10, 0),
        LocalDateTime.of(2026, 10, 26, 10, 0)
    );

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CollectionPointService collectionPointService;

    @Test
    void createReturnsCreated() throws Exception {
        when(collectionPointService.create(any(CollectionPointRequest.class))).thenReturn(RESPONSE);

        mockMvc.perform(post("/api/collection-points")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_BODY))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Ecoponto Centro"));
    }

    @Test
    void createWithoutNameReturnsBadRequest() throws Exception {
        String body = """
            {
              "address": "Rua das Flores, 100",
              "acceptedMaterials": "Vidro"
            }
            """;

        mockMvc.perform(post("/api/collection-points")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("O nome é obrigatório"));
    }

    @Test
    void findAllReturnsList() throws Exception {
        when(collectionPointService.findAll()).thenReturn(List.of(RESPONSE));

        mockMvc.perform(get("/api/collection-points"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].acceptedMaterials").value("Vidro, papel, eletrônicos"));
    }

    @Test
    void findByIdNotFoundReturnsNotFound() throws Exception {
        when(collectionPointService.findById(99L)).thenThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Ecoponto não encontrado")
        );

        mockMvc.perform(get("/api/collection-points/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Ecoponto não encontrado"));
    }

    @Test
    void updateReturnsOk() throws Exception {
        when(collectionPointService.update(eq(1L), any(CollectionPointRequest.class))).thenReturn(RESPONSE);

        mockMvc.perform(put("/api/collection-points/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_BODY))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/collection-points/1"))
            .andExpect(status().isNoContent());

        verify(collectionPointService).delete(1L);
    }
}
