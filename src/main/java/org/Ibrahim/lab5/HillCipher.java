package org.Ibrahim.lab5;

import java.util.Scanner;

public class HillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Input the 2x2 key matrix
        System.out.println("Enter the 2x2 key matrix (row by row, space-separated):");
        int[][] keyMatrix = new int[2][2];
        for (int i = 0; i < 2; i++) {
            keyMatrix[i][0] = scanner.nextInt();
            keyMatrix[i][1] = scanner.nextInt();
        }

        // Validate the key matrix
        int determinant = calculateDeterminant(keyMatrix);
        int modInverse = modInverse(determinant, 26);
        if (modInverse == -1) {
            System.out.println("The key matrix is not invertible. Please provide a valid matrix.");
            return; // Exit main
        }

        // Input the plaintext
        System.out.print("Enter a 4-letter word (only uppercase letters): ");
        String plaintext = scanner.next().toUpperCase();
        if (plaintext.length() != 4 || !plaintext.matches("[A-Z]+")) {
            System.out.println("Invalid input. Please enter exactly 4 uppercase letters.");
            // *** SYNTAX ERROR FIXED ***
            // The return statement was misplaced outside the if-block.
            return; // Exit main
        }

        // Convert plaintext to numerical form
        // *** SYNTAX ERROR FIXED *** (Missing [])
        int[] plaintextNumerical = convertToNumerical(plaintext);

        // Encrypt the plaintext
        int[] ciphertextNumerical = encrypt(plaintextNumerical, keyMatrix);
        String ciphertext = convertToText(ciphertextNumerical);

        // Display encryption calculations
        System.out.println("Encryption calculations:");
        displayMatrixCalculation(plaintextNumerical, keyMatrix, ciphertextNumerical);
        System.out.println("Encrypted Text: " + ciphertext);

        // Calculate the inverse key matrix
        // *** SYNTAX ERROR FIXED *** (Missing type, mangled name)
        int[][] inverseKeyMatrix = calculateInverseKeyMatrix(keyMatrix, modInverse);

        // Decrypt the ciphertext
        int[] decryptedNumerical = decrypt(ciphertextNumerical, inverseKeyMatrix);
        String decryptedText = convertToText(decryptedNumerical);

        // Display decryption calculations
        System.out.println("\nDecryption calculations:");
        displayMatrixCalculation(ciphertextNumerical, inverseKeyMatrix, decryptedNumerical);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }

    // Convert text to numerical representation
    private static int[] convertToNumerical(String text) {
        int[] numerical = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            numerical[i] = text.charAt(i) - 'A';
        }
        return numerical;
    }

    // Convert numerical representation back to text
    private static String convertToText(int[] numbers) {
        StringBuilder text = new StringBuilder();
        for (int num : numbers) {
            text.append((char) ((num + 26) % 26 + 'A'));
        }
        return text.toString();
    }

    // Encrypt the plaintext using the key matrix
    private static int[] encrypt(int[] plaintext, int[][] keyMatrix) {
        return matrixMultiply(keyMatrix, plaintext);
    }

    // Decrypt the ciphertext using the inverse key matrix
    private static int[] decrypt(int[] ciphertext, int[][] inverseKeyMatrix) {
        return matrixMultiply(inverseKeyMatrix, ciphertext);
    }

    // Matrix multiplication for 2x2 matrix and 4x1 vector
    // (processing as two 2x1 vectors)
    private static int[] matrixMultiply(int[][] matrix, int[] vector) {
        int[] result = new int[vector.length];
        for (int i = 0; i < 2; i++) {
            result[i] = (matrix[i][0] * vector[0] + matrix[i][1] * vector[1]) % 26;
            result[i + 2] = (matrix[i][0] * vector[2] + matrix[i][1] * vector[3]) % 26;
        }
        return result;
    }

    // Display the matrix multiplication step-by-step
    private static void displayMatrixCalculation(int[] vector, int[][] matrix, int[] result) {
        System.out.printf("Matrix:\n[%d %d]\n[%d %d]\n", matrix[0][0], matrix[0][1], matrix[1][0], matrix[1][1]);
        System.out.printf("Vector: [%d %d %d %d]\n", vector[0], vector[1], vector[2], vector[3]);
        System.out.printf("Result: [%d %d %d %d]\n", result[0], result[1], result[2], result[3]);
    }

    // Calculate determinant of a 2x2 matrix
    private static int calculateDeterminant(int[][] matrix) {
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
    }

    // Modular inverse of a number modulo 26
    private static int modInverse(int number, int mod) {
        number = (number % mod + mod) % mod;
        for (int i = 1; i < mod; i++) {
            if ((number * i) % mod == 1) {
                return i;
            }
        }
        return -1; // No modular inverse exists
    }

    // Calculate inverse of 2x2 key matrix
    private static int[][] calculateInverseKeyMatrix(int[][] matrix, int modInverse) {
        int[][] inverse = new int[2][2];
        inverse[0][0] = matrix[1][1];
        inverse[1][1] = matrix[0][0];
        inverse[0][1] = -matrix[0][1];
        inverse[1][0] = -matrix[1][0];

        // Multiply by modular inverse and apply mod 26
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverse[i][j] = (inverse[i][j] * modInverse % 26 + 26) % 26;
            }
        }
        return inverse;
    }
}