package org.example.mutantes.service;

import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular estadísticas correctamente (Ratio 0.4)")
    void testGetStatsNormalRatio() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    @DisplayName("Debe manejar división por cero (0 humanos) y retornar 0.0")
    void testGetStatsNoHumans() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio()); // Aserción corregida para validar 0.0
    }

    @Test
    @DisplayName("Debe retornar 0.0 si no hay mutantes ni humanos (sin datos)")
    void testGetStatsNoData() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    @DisplayName("Debe retornar ratio 1.0 si las cantidades son iguales")
    void testGetStatsEqualCounts() {
        when(repository.countByIsMutant(true)).thenReturn(50L);
        when(repository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse response = statsService.getStats();

        assertEquals(50, response.getCountMutantDna());
        assertEquals(50, response.getCountHumanDna());
        assertEquals(1.0, response.getRatio());
    }
}
