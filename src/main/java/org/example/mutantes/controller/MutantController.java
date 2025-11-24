package org.example.mutantes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Detectar si un humano es mutante",
            description = "Recibe una secuencia de ADN y retorna 200 si es mutante, 403 si es humano.")
    @ApiResponse(responseCode = "200",
            description = "Es Mutante",
            content = @Content(schema = @Schema(hidden = true))) // Respuesta vacía
    @ApiResponse(responseCode = "403",
            description = "No es Mutante",
            content = @Content(schema = @Schema(hidden = true))) // Respuesta vacía
    @ApiResponse(responseCode = "400",
            description = "ADN inválido") // Documentación para validación del DTO
    public ResponseEntity<Void> detectMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());

        if (isMutant) {
            return ResponseEntity.ok().build(); // Retorna 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Retorna 403 Forbidden
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de verificaciones",
            description = "Retorna el número de ADN mutantes y humanos, y su proporción.")
    @ApiResponse(responseCode = "200",
            description = "Estadísticas obtenidas con éxito.",
            content = @Content(schema = @Schema(implementation = StatsResponse.class))) // Documenta el esquema StatsResponse
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}