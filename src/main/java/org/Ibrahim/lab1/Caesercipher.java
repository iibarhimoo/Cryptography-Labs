package org.Ibrahim.lab1;

import java.io.*;

public class Caesercipher {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String ptext, int cserkey) {
        ptext = ptext.toLowerCase();
        String ctext = "";
        for (int i = 0; i < ptext.length(); i++) {
            char ch = ptext.charAt(i);
            int plainnumeric = ALPHABET.indexOf(ch);

            // Only process alphabetic characters
            if (plainnumeric != -1) {
                int ciphernumeric = (plainnumeric + cserkey) % 26;
                char cipherchar = ALPHABET.charAt(ciphernumeric);
                ctext += cipherchar;
            } else {
                // Keep non-alphabetic characters as they are
                ctext += ch;
            }
        }
        return ctext;
    }

    public static String decrypt(String ctext, int cserkey) {
        String ptext = "";
        for (int i = 0; i < ctext.length(); i++) {
            char ch = ctext.charAt(i);
            int ciphernumeric = ALPHABET.indexOf(ch);

            // Only process alphabetic characters
            if (ciphernumeric != -1) {
                int plainnumeric = (ciphernumeric - cserkey) % 26;
                if (plainnumeric < 0) {
                    plainnumeric = ALPHABET.length() + plainnumeric;
                }
                char plainchar = ALPHABET.charAt(plainnumeric);
                ptext += plainchar;
            } else {
                // Keep non-alphabetic characters as they are
                ptext += ch;
            }
        }
        return ptext;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cserkey;

        System.out.println("--- Caeser Cipher Encryption & Decryption ---");
        System.out.println("Enter the PLAIN TEXT for Encryption:");
        String plaintext = br.readLine();

        // Loop until a valid key is entered
        while (true) {
            try {
                System.out.println("Enter the CAESER KEY (a number between 0 and 25):");
                String keyInput = br.readLine();
                cserkey = Integer.parseInt(keyInput);

                if (cserkey >= 0 && cserkey <= 25) {
                    break; // Exit the loop if the key is valid
                } else {
                    System.out.println("Invalid key. The number must be between 0 and 25. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. Please try again.");
            }
        }

        // Encryption
        System.out.println("\n--- ENCRYPTION ---");
        String ciphertext = encrypt(plaintext, cserkey);
        System.out.println("CIPHER TEXT: " + ciphertext);

        // Decryption
        System.out.println("\n--- DECRYPTION ---");
        String decryptedtext = decrypt(ciphertext, cserkey);
        System.out.println("PLAIN TEXT: " + decryptedtext);
    }
}
