package org.Ibrahim.lab7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TranspositionCipher {

    /**
     * Encrypts the plaintext using columnar transposition.
     * @param plaintext The message to encrypt.
     * @param key The keyword to use for transposition.
     * @return The encrypted ciphertext.
     */
    public static String encrypt(String plaintext, String key) {
        // Remove spaces and convert to uppercase
        plaintext = plaintext.replaceAll(" ", "").toUpperCase();
        key = key.toUpperCase();

        int numCols = key.length();
        // Calculate rows, rounding up to ensure all text fits
        int numRows = (int) Math.ceil((double) plaintext.length() / numCols);

        char[][] matrix = new char[numRows][numCols];
        int textIndex = 0;

        // Fill the matrix row by row
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (textIndex < plaintext.length()) {
                    matrix[i][j] = plaintext.charAt(textIndex++);
                } else {
                    matrix[i][j] = 'X'; // Pad with 'X'
                }
            }
        }

        // Create an array of column indices: [0, 1, 2, 3, 4, 5]
        Integer[] colOrder = new Integer[numCols];
        for (int i = 0; i < numCols; i++) {
            colOrder[i] = i;
        }

        // Sort the column indices based on the characters in the key
        // For key "ZEBRAS", the order becomes [4, 2, 1, 3, 5, 0]
        Arrays.sort(colOrder, Comparator.comparingInt(key::charAt));

        StringBuilder ciphertext = new StringBuilder();
        // Read columns in the sorted order
        for (int col : colOrder) {
            for (int i = 0; i < numRows; i++) {
                ciphertext.append(matrix[i][col]);
            }
        }
        return ciphertext.toString();
    }

    /**
     * Decrypts the ciphertext using columnar transposition.
     * @param ciphertext The message to decrypt.
     * @param key The keyword used for encryption.
     * @return The decrypted plaintext.
     */
    public static String decrypt(String ciphertext, String key) {
        key = key.toUpperCase();
        int numCols = key.length();

        // Rows do not need ceiling, as ciphertext length is already a multiple of key length
        int numRows = ciphertext.length() / numCols;

        char[][] matrix = new char[numRows][numCols];

        // Determine column order based on key (same as encryption)
        Integer[] colOrder = new Integer[numCols];
        for (int i = 0; i < numCols; i++) {
            colOrder[i] = i;
        }
        Arrays.sort(colOrder, Comparator.comparingInt(key::charAt));

        int cipherIndex = 0;
        // Fill the matrix column by column, in the sorted key order
        for (int col : colOrder) {
            for (int i = 0; i < numRows; i++) {
                matrix[i][col] = ciphertext.charAt(cipherIndex++);
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        // Read the matrix row by row to get the original order
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                decryptedText.append(matrix[i][j]);
            }
        }

        // Remove one or more padding 'X's from the very end of the string
        return decryptedText.toString().replaceAll("X+$", "");
    }

    /**
     * Main method to run the program with a user menu.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Transposition Cipher Menu ---");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter plaintext: ");
                    String plaintext = scanner.nextLine();
                    System.out.print("Enter key: ");
                    String keyEncrypt = scanner.nextLine();

                    String encryptedText = encrypt(plaintext, keyEncrypt);
                    System.out.println("Encrypted text: " + encryptedText);
                    break;

                case 2:
                    System.out.print("Enter ciphertext: ");
                    String ciphertext = scanner.nextLine();
                    System.out.print("Enter key: ");
                    String keyDecrypt = scanner.nextLine();

                    String decryptedText = decrypt(ciphertext, keyDecrypt);
                    System.out.println("Decrypted text: " + decryptedText);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 0.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

