package org.Ibrahim.lab7;

import java.util.*;

class TranspositionCipher {
    public static void main(String[] args) {
        System.out.println("*******TRANSPOSITION CIPHER*******");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the choice:");
        System.out.println("1. Plain text to cipher text.");
        System.out.println("2. cipher text to plain text.");
        int choice = in.nextInt();

        switch (choice) {
            case 1:
                findTranspositionCipher();
                break;
            case 2:
                DecryptTranspositionCipher();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public static void findTranspositionCipher() {
        Scanner in = new Scanner(System.in);
        String plainText;
        String cipherText = "", jpt = "XYZ"; // Simpler padding
        int c = 0, l = 0, v = 0, i = 0, padding = 0;

        // *** SYNTAX ERROR FIXED *** (Added missing [])
        char mat[][];
        int key[] = { 4, 3, 1, 2, 5, 6, 7 };

        System.out.println("Enter the plain text:");
        plainText = in.nextLine().replaceAll("\\s", ""); // Read line, remove spaces

        // Calculate rows needed
        v = (int) Math.ceil((double) plainText.length() / key.length);
        mat = new char[v][key.length];

        // --- Simplified Matrix-Fill Loop ---
        c = 0; // index for plaintext
        for (i = 0; i < v; i++) { // rows
            for (int j = 0; j < key.length; j++) { // cols
                if (c < plainText.length()) {
                    mat[i][j] = plainText.charAt(c++);
                } else {
                    // Pad with 'X', 'Y', 'Z', etc.
                    mat[i][j] = jpt.charAt(padding++ % jpt.length());
                }
            }
        }

        // --- Read-out Loop (Original logic was correct) ---
        c = 1; // key order (1, 2, 3...)
        while (c <= key.length) {
            for (i = 0; i < key.length; i++) { // i is the column index
                if (key[i] == c) {
                    for (l = 0; l < v; l++) { // l is the row index
                        cipherText = cipherText + mat[l][i];
                    }
                }
            }
            c++;
        }

        cipherText = cipherText.toUpperCase();
        System.out.println("\nThe Cipher Text is:\n" + cipherText);
    }

    public static void DecryptTranspositionCipher() {
        Scanner in = new Scanner(System.in);
        String plainText = "", cipherText;
        int c = 0, l = 1, v = 0, i = 0, j = 0, k = 0;

        // *** SYNTAX ERROR FIXED *** (Added missing [])
        char mat[][];
        int key[] = { 4, 3, 1, 2, 5, 6, 7 };

        System.out.println("Enter the Cipher text:");
        cipherText = in.nextLine();

        // Calculate number of rows
        v = (cipherText.length() / key.length);
        if (cipherText.length() % key.length != 0) {
            System.out.println("Ciphertext length is not valid for this key.");
            return;
        }

        mat = new char[v][key.length];

        // --- Fill-in Loop (Original logic was correct) ---
        c = 0; // key order (0, 1, 2...)
        while (c < key.length) {
            for (j = 0; j < key.length; j++) { // j is col index
                // *** SYNTAX ERROR FIXED *** (==)
                if (key[j] == c + 1) {
                    for (i = 0; i < v; i++) { // i is row index
                        mat[i][j] = cipherText.charAt(k++);
                    }
                }
            }
            c++;
        }

        // --- Read-out Loop (Original logic was correct) ---
        for (int p = 0; p < v; p++) {
            for (int q = 0; q < key.length; q++) {
                plainText = plainText + mat[p][q];
            }
        }

        System.out.println("\nThe Plain text is:\n" + plainText);
    }
}