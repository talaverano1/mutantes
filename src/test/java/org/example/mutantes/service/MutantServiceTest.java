package org.example.mutantes.service;

import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.exception.InvalidDnaException;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    @DisplayName("Debe analizar ADN mutante y guardarlo si no existe")
    void testAnalyzeMutantDnaAndSave() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe analizar ADN humano y guardarlo si no existe")
    void testAnalyzeHumanDnaAndSave() {
        String[] dna = {"ATGC", "GTAG", "CATA", "TCGA"}; // Ejemplo humano

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false); // Aquí cambia a FALSE

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result); // Debe ser falso
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe retornar resultado cacheado si ya existe en BD")
    void testReturnCachedResult() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);

        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        // Verificar que NO se llamó al detector ni a save (ahorro de recursos)
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar InvalidDnaException si el ADN es nulo (Defensive Programming)")
    void testShouldThrowExceptionWhenDnaIsNull() {
        assertThrows(InvalidDnaException.class, () -> mutantService.analyzeDna(null));
    }

    @Test
    @DisplayName("Debe lanzar InvalidDnaException si el ADN es vacío")
    void testShouldThrowExceptionWhenDnaIsEmpty() {
        assertThrows(InvalidDnaException.class, () -> mutantService.analyzeDna(new String[]{}));
    }
}
