package org.Ibrahim.lab6;

import java.io.*;
import java.util.*;

class DoubleTransposition {
    public static void main(String args[]) {
        EncryptAndDecrypt ned = new EncryptAndDecrypt();
        ned.input();
    }
}

class EncryptAndDecrypt {
    Scanner sc = new Scanner(System.in);

    /**
     * Displays the main menu and handles user choices for encryption,
     * decryption, or exiting the program.
     */
    void input() {
        int ch;
        do {
            System.out.println("\n\t\t*****ENTER*****");
            System.out.println("\t1.Encrypt");
            System.out.println("\t2.Decrypt");
            System.out.println("\t0.Exit");
            System.out.print("Enter your choice: ");

            ch = sc.nextInt();

            switch (ch) {
                case 1: {
                    encrypt();
                    break;
                }
                case 2: {
                    decrypt();
                    break;
                }
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 0.");
            }
        } while (ch != 0);
    }

    /**
     * Encrypts a plaintext message using Double Transposition.
     */
    void encrypt() {
        String ip;
        char text[][], enc[][];
        int i, j, row[], col[], r, c;
        int k = 0, m, n;

        System.out.print("Enter The Plain Text: ");
        ip = sc.next(); // Read the plaintext

        System.out.print("Enter The Number Of Rows: ");
        r = sc.nextInt();
        System.out.print("Enter The Number Of Columns: ");
        c = sc.nextInt();

        // Check if plaintext fits in the grid
        if (ip.length() > r * c) {
            System.out.println("Plaintext is too long for the given matrix size.");
            return;
        }

        text = new char[r][c];
        enc = new char[r][c];
        row = new int[r];
        col = new int[c];

        // Fill the grid with plaintext, padding with 'X'
        for (i = 0; i < r; i++) {
            for (j = 0; j < c; j++) {
                if (k < ip.length()) {
                    text[i][j] = ip.charAt(k);
                    k++;
                } else {
                    text[i][j] = 'X'; // Pad with 'X'
                }
            }
        }

        // Get the numeric row key
        System.out.println("Enter The Row Key (e.g., 3 2 1): ");
        for (i = 0; i < r; i++)
            row[i] = (sc.nextInt() - 1); // -1 to convert from 1-based to 0-based index

        // Get the numeric column key
        System.out.println("Enter The Column Key (e.g., 4 2 1 3): ");
        for (i = 0; i < c; i++)
            col[i] = (sc.nextInt() - 1); // -1 to convert from 1-based to 0-based index

        // Perform the double transposition
        System.out.print("The Cipher Text Is: ");
        for (i = 0; i < r; i++) {
            m = row[i]; // Get permuted row index
            for (j = 0; j < c; j++) {
                n = col[j]; // Get permuted column index
                enc[i][j] = text[m][n]; // Apply both transpositions
                System.out.print("" + Character.toUpperCase(enc[i][j]));
            }
        }
        System.out.println(); // Added for cleaner output
    }

    /**
     * Decrypts a ciphertext message using Double Transposition.
     */
    void decrypt() {
        String ip;
        char text[][], enc[][];
        int i, j, row[], col[], r, c;
        int k = 0, m, n;

        System.out.print("Enter The Cipher Text: ");
        ip = sc.next();

        System.out.print("Enter The Number Of Rows: ");
        r = sc.nextInt();
        System.out.print("Enter The Number Of Columns: ");
        c = sc.nextInt();

        // Check for mismatch
        if (ip.length() != r * c) {
            System.out.println("Ciphertext length does not match matrix size.");
            return;
        }

        text = new char[r][c];
        enc = new char[r][c];
        row = new int[r];
        col = new int[c];

        // Fill the grid with ciphertext
        for (i = 0; i < r; i++) {
            for (j = 0; j < c; j++) {
                text[i][j] = ip.charAt(k);
                k++;
            }
        }

        // Get the same row key used for encryption
        System.out.println("Enter The Row Key (e.g., 3 2 1): ");
        for (i = 0; i < r; i++)
            row[i] = (sc.nextInt() - 1);

        // Get the same column key used for encryption
        System.out.println("Enter The Column Key (e.g., 4 2 1 3): ");
        for (i = 0; i < c; i++)
            col[i] = (sc.nextInt() - 1);

        // Perform the decryption (reverse transposition)
        for (i = 0; i < r; i++) {
            m = row[i]; // Get permuted row index
            for (j = 0; j < c; j++) {
                n = col[j]; // Get permuted column index
                enc[m][n] = text[i][j]; // Reverse the mapping
            }
        }

        // Read the plaintext from the un-shuffled grid
        System.out.print("The Retrieved Plain Text Is: ");
        for (i = 0; i < r; i++) {
            for (j = 0; j < c; j++) {
                System.out.print("" + Character.toLowerCase(enc[i][j]));
            }
        }
        System.out.println(); // Added for cleaner output
    }
}
