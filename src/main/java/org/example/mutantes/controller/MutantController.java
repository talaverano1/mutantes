package org.example.mutantes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.service.MutantService;
import org.example.mutantes.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detectar mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService; // (Implementar lógica similar a MutantService)

    @PostMapping("/mutant")
    @Operation(summary = "Detectar si un humano es mutante")
    @ApiResponse(responseCode = "200", description = "Es Mutante")
    @ApiResponse(responseCode = "403", description = "No es Mutante")
    public ResponseEntity<Void> detectMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de verificaciones")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}