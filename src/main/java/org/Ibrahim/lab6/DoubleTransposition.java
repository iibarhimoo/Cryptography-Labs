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

    void input() {
        int ch;
        do {
            System.out.println("\n\t\t*****ENTER*****");
            System.out.println("\t1.Encrypt");
            System.out.println("\t2.Decrypt");
            System.out.println("\t0.Exit");

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
            }
        } while (ch != 0); // *** SYNTAX ERROR FIXED ***
    }

    void encrypt() {
        // *** SYNTAX ERROR FIXED *** (Used String, not StringBuilder)
        String ip;
        // *** SYNTAX ERROR FIXED *** (Added missing [])
        char text[][], enc[][];
        int i, j, row[], col[], r, c;
        int k = 0, m, n;

        System.out.print("Enter The Plain Text ");
        // *** BUG FIXED *** (Cleaned up scanner input)
        ip = sc.next();

        System.out.print("Enter The Number Of Rows ");
        r = sc.nextInt();
        System.out.print("Enter The Number Of Columns ");
        c = sc.nextInt();

        // Add check for plaintext length
        if (ip.length() > r * c) {
            System.out.println("Plaintext is too long for the given matrix size.");
            return;
        }

        text = new char[r][c];
        enc = new char[r][c];
        row = new int[r];
        col = new int[c];

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

        System.out.println("Enter The Row Key ");
        for (i = 0; i < r; i++)
            row[i] = (sc.nextInt() - 1);

        System.out.println("Enter The Column Key ");
        for (i = 0; i < c; i++)
            col[i] = (sc.nextInt() - 1);

        k = 0;
        System.out.print("The Cipher Text Is ");
        for (i = 0; i < r; i++) {
            m = row[i];
            for (j = 0; j < c; j++) {
                n = col[j];
                enc[i][j] = text[m][n];
                System.out.print("" + Character.toUpperCase(enc[i][j]));
            }
        }
        System.out.println(); // Added for cleaner output
    }

    void decrypt() {
        String ip, t;
        // *** SYNTAX ERROR FIXED *** (Added missing [])
        char text[][], enc[][];
        int i, j, row[], col[], r, c;
        int k = 0, m, n;

        System.out.print("Enter The Cipher Text ");
        ip = sc.next();

        System.out.print("Enter The Number Of Rows ");
        r = sc.nextInt();
        System.out.print("Enter The Number Of Columns ");
        c = sc.nextInt();

        if (ip.length() != r * c) {
            System.out.println("Ciphertext length does not match matrix size.");
            return;
        }

        text = new char[r][c];
        enc = new char[r][c];
        row = new int[r];
        col = new int[c];

        for (i = 0; i < r; i++) {
            for (j = 0; j < c; j++) {
                text[i][j] = ip.charAt(k);
                k++;
            }
        }

        System.out.println("Enter The Row Key ");
        for (i = 0; i < r; i++)
            row[i] = (sc.nextInt() - 1);

        System.out.println("Enter The Column Key ");
        for (i = 0; i < c; i++)
            col[i] = (sc.nextInt() - 1);

        k = 0;
        for (i = 0; i < r; i++) {
            m = row[i];
            for (j = 0; j < c; j++) {
                n = col[j];
                enc[m][n] = text[i][j];
            }
        }

        System.out.print("The Retrieved Plain Text Is ");
        for (i = 0; i < r; i++) {
            for (j = 0; j < c; j++) {
                System.out.print("" + Character.toLowerCase(enc[i][j]));
            }
        }
        System.out.println(); // Added for cleaner output
    }
}
