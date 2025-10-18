package org.Ibrahim.lab3;

import java.util.Base64;
import java.util.Scanner;

public class OneTimePadCipher {

    /**
     * Encrypts the plaintext using a provided key.
     *
     * @param plaintext The message to encrypt.
     * @param key The key for encryption. MUST be the same length as the plaintext.
     * @return A Base64-encoded string representing the ciphertext.
     */
    public static String encrypt(String plaintext, String key) {
        try {
            byte[] plaintextBytes = plaintext.getBytes("UTF-8");
            byte[] keyBytes = key.getBytes("UTF-8");

            // Perform XOR operation for encryption
            byte[] ciphertextBytes = new byte[plaintextBytes.length];
            for (int i = 0; i < plaintextBytes.length; i++) {
                ciphertextBytes[i] = (byte) (plaintextBytes[i] ^ keyBytes[i]);
            }

            // Encode the ciphertext to a Base64 string for easy transfer
            return Base64.getEncoder().encodeToString(ciphertextBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypts the ciphertext using the provided key.
     *
     * @param ciphertext The Base64-encoded ciphertext.
     * @param key The key for decryption. MUST be the same length as the original plaintext.
     * @return The original plaintext.
     */
    public static String decrypt(String ciphertext, String key) {
        try {
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            byte[] keyBytes = key.getBytes("UTF-8");

            // Perform XOR operation for decryption
            byte[] plaintextBytes = new byte[ciphertextBytes.length];
            for (int i = 0; i < ciphertextBytes.length; i++) {
                plaintextBytes[i] = (byte) (ciphertextBytes[i] ^ keyBytes[i]);
            }

            return new String(plaintextBytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- One-Time Pad Cipher ---");
        System.out.println("1. Encrypt Text");
        System.out.println("2. Decrypt Text");
        System.out.print("Enter your choice (1 or 2): ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            // Encryption
            System.out.print("Enter plaintext: ");
            String plaintext = scanner.nextLine();

            System.out.print("Enter key (must be same length as plaintext): ");
            String key = scanner.nextLine();

            if (plaintext.isEmpty() || key.isEmpty()) {
                System.out.println("Plaintext and key cannot be empty.");
                return;
            }
            if (plaintext.length() != key.length()) {
                System.out.println("Error: Plaintext and key must be the same length.");
                return;
            }

            String encryptedResult = encrypt(plaintext, key);
            if (encryptedResult != null) {
                System.out.println("\nEncrypted message:");
                System.out.println(encryptedResult);
            }

        } else if (choice.equals("2")) {
            // Decryption
            System.out.print("Enter ciphertext (Base64 format): ");
            String ciphertext = scanner.nextLine();

            System.out.print("Enter key (must be same length as original plaintext): ");
            String key = scanner.nextLine();

            if (ciphertext.isEmpty() || key.isEmpty()) {
                System.out.println("Ciphertext and key cannot be empty.");
                return;
            }

            // Note: We cannot validate ciphertext and key length here without the original plaintext
            // The length check logic in the encrypt method is what ensures correctness.

            String decryptedResult = decrypt(ciphertext, key);
            if (decryptedResult != null) {
                System.out.println("\nDecrypted message:");
                System.out.println(decryptedResult);
            }
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }

        scanner.close();
    }
}