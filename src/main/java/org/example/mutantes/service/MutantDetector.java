package org.example.mutantes.service;
import org.springframework.stereotype.Service;

@Service
public class MutantDetector {

    // Constante para la secuencia (4 letras iguales)
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        int sequenceCount = 0;

        // Optimización 1: Convertir a char[][] para acceso rápido O(1)
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        // Optimización 2: Single Pass (Recorremos la matriz una sola vez)
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Optimización 3: Boundary Checking (Solo buscamos si hay espacio)

                // Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        // Optimización 4: Early Termination
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

                // Diagonal Principal (\)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Diagonal Invertida (/)
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkAntiDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // Optimización 5: Direct Comparison (Sin bucles internos)
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

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col+1] &&
                base == matrix[row+2][col+2] &&
                base == matrix[row+3][col+3];
    }

    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return base == matrix[row+1][col-1] &&
                base == matrix[row+2][col-2] &&
                base == matrix[row+3][col-3];
    }
}