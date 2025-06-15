package com.pdl.pdl.service;

import com.pdl.pdl.entity.Depense;
import com.pdl.pdl.repository.DepenseRepository;
import com.pdl.pdl.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepenseServiceTest {

    @Mock
    private DepenseRepository depenseRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private DepenseService depenseService;

    private Depense testDepense;

    @BeforeEach
    void setUp() {
        testDepense = new Depense();
        testDepense.setId(1L);
        testDepense.setLibelle("Test Depense");
    }

    @Test
    void getAllDepenses_ShouldReturnAllDepenses() {
        List<Depense> expectedDepenses = Arrays.asList(testDepense);
        when(depenseRepository.findAll()).thenReturn(expectedDepenses);

        List<Depense> result = depenseService.getAllDepenses();

        assertEquals(1, result.size());
        assertEquals(testDepense, result.get(0));
        verify(depenseRepository).findAll();
    }


}
