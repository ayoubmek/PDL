package com.pdl.pdl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdl.pdl.PdlApplication; // Make sure to import your main application
import com.pdl.pdl.entity.Depense;
import com.pdl.pdl.entity.StatutDepense;
import com.pdl.pdl.service.DepenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepenseController.class)
@ContextConfiguration(classes = PdlApplication.class) // Explicitly specify main config
class DepenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepenseService depenseService;

    @Autowired
    private ObjectMapper objectMapper;

    // Fixed test date
    private static final LocalDate TEST_DATE = LocalDate.of(2024, Month.JUNE, 3);
    private static final LocalDateTime TEST_DATETIME = LocalDateTime.of(2024, Month.JUNE, 3, 20, 0, 0);

    private Depense createSampleDepense() {
        Depense depense = new Depense();
        depense.setId(1L);
        depense.setLibelle("Test depense");
        depense.setMontant(new BigDecimal("100.00"));
        depense.setStatut(StatutDepense.SOUMIS);
        return depense;
    }

    @Test
    void createDepense_ShouldReturnCreatedDepense() throws Exception {
        Depense depense = createSampleDepense();
        when(depenseService.saveDepense(any(Depense.class))).thenReturn(depense);

        mockMvc.perform(post("/api/depenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Test depense"))
                .andExpect(jsonPath("$.dateDepense").value("2024-06-03")) // Verify fixed date
                .andExpect(jsonPath("$.statut").value("SOUMIS"));

        verify(depenseService, times(1)).saveDepense(any(Depense.class));
    }

    @Test
    void getAllDepenses_ShouldReturnEmptyList_WhenNoDepensesExist() throws Exception {
        when(depenseService.getAllDepenses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/depenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(depenseService, times(1)).getAllDepenses();
    }

}