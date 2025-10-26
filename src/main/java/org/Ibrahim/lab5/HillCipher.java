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
            return;
        }

        // *** MODIFIED SECTION: INPUT & PADDING ***
        // Input the plaintext
        System.out.print("Enter a word (only uppercase letters): "); // Changed prompt
        String plaintext = scanner.next().toUpperCase();

        // Validate for uppercase letters only
        if (!plaintext.matches("[A-Z]+")) {
            System.out.println("Invalid input. Please enter only uppercase letters.");
            return;
        }

        // Add padding if length is odd
        if (plaintext.length() % 2 != 0) {
            plaintext += "X"; // Pad with 'X'
            System.out.println("Plaintext was padded to: " + plaintext);
        }
        // *** END MODIFIED SECTION ***

        // Convert plaintext to numerical form
        int[] plaintextNumerical = convertToNumerical(plaintext);

        // Encrypt the plaintext
        int[] ciphertextNumerical = encrypt(plaintextNumerical, keyMatrix);
        String ciphertext = convertToText(ciphertextNumerical);

        // Display encryption calculations
        System.out.println("Encryption calculations:");
        displayMatrixCalculation(plaintextNumerical, keyMatrix, ciphertextNumerical);
        System.out.println("Encrypted Text: " + ciphertext);

        // Calculate the inverse key matrix
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

    // *** MODIFIED SECTION: GENERALIZED MATRIX MULTIPLICATION ***
    // Matrix multiplication for 2x2 matrix and vector of any even length
    private static int[] matrixMultiply(int[][] matrix, int[] vector) {
        int[] result = new int[vector.length];

        // Loop through the vector in pairs (e.g., 0,1 then 2,3 then 4,5 ...)
        for (int k = 0; k < vector.length; k += 2) {
            // C1 = (K11*P1 + K12*P2) mod 26
            result[k] = (matrix[0][0] * vector[k] + matrix[0][1] * vector[k + 1]) % 26;
            // C2 = (K21*P1 + K22*P2) mod 26
            result[k + 1] = (matrix[1][0] * vector[k] + matrix[1][1] * vector[k + 1]) % 26;

            // Ensure results are positive
            if (result[k] < 0) result[k] += 26;
            if (result[k+1] < 0) result[k+1] += 26;
        }
        return result;
    }

    // *** MODIFIED SECTION: GENERALIZED DISPLAY ***
    // Display the matrix multiplication step-by-step for any even length
    private static void displayMatrixCalculation(int[] vector, int[][] matrix, int[] result) {
        System.out.printf("Matrix:\n[%d %d]\n[%d %d]\n", matrix[0][0],
                matrix[0][1], matrix[1][0], matrix[1][1]);

        System.out.print("Vector: [");
        for (int val : vector) {
            System.out.print(val + " ");
        }
        System.out.println("]");

        System.out.print("Result: [");
        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println("]");
    }
    // *** END MODIFIED SECTIONS ***

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