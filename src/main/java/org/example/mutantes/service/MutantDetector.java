package org.example.mutantes.service;
import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {

        if (dna == null || dna.length < SEQUENCE_LENGTH) {
            return false;
        }

        final int n = dna.length;

        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            // CORRECCIÓN 1: Validación de NxN y Caracteres Integrada
            if (dna[i] == null || dna[i].length() != n || !isValidRow(dna[i])) {
                return false;
            }
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Diagonal Descendente (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Diagonal Ascendente (↗) - CORRECCIÓN 2: Ajuste de la condición
                // Necesita 4 filas debajo/incluida (row >= 3) y 4 columnas a la derecha/incluida (col <= n - 4)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // MÉTODO AÑADIDO: Valida la fila contra A, T, C, G (Requisito de la rúbrica)
    private boolean isValidRow(String row) {
        for (char c : row.toCharArray()) {
            if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                return false;
            }
        }
        return true;
    }

    // El método checkAntiDiagonal fue renombrado a checkDiagonalAscending y ajustado
    // El método checkDiagonal fue renombrado a checkDiagonalDescending

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row][col+1] &&
                base == matrix[row][col+2] &&
                base == matrix[row][col+3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col] &&
                base == matrix[row+2][col] &&
                base == matrix[row+3][col];
    }

    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col+1] &&
                base == matrix[row+2][col+2] &&
                base == matrix[row+3][col+3];
    }

    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        // En diagonal ascendente, la fila se DECREMENTA y la columna se INCREMENTA
        return base == matrix[row-1][col+1] &&
                base == matrix[row-2][col+2] &&
                base == matrix[row-3][col+3];
    }
}