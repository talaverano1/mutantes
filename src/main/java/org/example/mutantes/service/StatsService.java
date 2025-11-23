package org.example.mutantes.service;

import lombok.RequiredArgsConstructor;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        // 1. Consultar a la BD usando los métodos que creamos en el Repository
        long countMutant = dnaRecordRepository.countByIsMutant(true);
        long countHuman = dnaRecordRepository.countByIsMutant(false);

        // 2. Calcular el ratio (evitando división por cero)
        double ratio = 0.0;
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        }

        // 3. Devolver el objeto con los datos
        return new StatsResponse(countMutant, countHuman, ratio);
    }
}