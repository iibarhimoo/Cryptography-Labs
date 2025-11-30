package org.Ibrahim.lab9;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

class DiffieHellman {

    /**
     * Encrypts a string using a simple shift cipher based on the shared key.
     * Note: In real world, you would use AES here.
     */
    public static String encrypt(String message, BigInteger key) {
        StringBuilder cipherText = new StringBuilder();
        // We use key % 26 so we can shift alphabet letters simply
        int shift = key.mod(BigInteger.valueOf(26)).intValue();

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                // (char - base + shift) % 26 + base
                char encryptedChar = (char) (((c - base + shift) % 26) + base);
                cipherText.append(encryptedChar);
            } else {
                // Keep spaces and symbols as is
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }

    /**
     * Decrypts a string using the shared key.
     */
    public static String decrypt(String message, BigInteger key) {
        StringBuilder plainText = new StringBuilder();
        int shift = key.mod(BigInteger.valueOf(26)).intValue();

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                // (char - base - shift) -> handle negative numbers by adding 26
                char decryptedChar = (char) (((c - base - shift + 26) % 26) + base);
                plainText.append(decryptedChar);
            } else {
                plainText.append(c);
            }
        }
        return plainText.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Diffie-Hellman Key Exchange ---");

        // 1. Agree on Global Public Numbers
        System.out.print("Enter prime number (p): ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Enter primitive root of " + p + " (g): ");
        BigInteger g = new BigInteger(scanner.nextLine());

        // 2. Alice generates keys
        System.out.print("Enter Alice's private key (XA) less than " + p + ": ");
        BigInteger x = new BigInteger(scanner.nextLine());
        BigInteger R1 = g.modPow(x, p); // Alice's Public Key (YA)
        System.out.println("Alice's Public Key (YA): " + R1);

        // 3. Bob generates keys
        System.out.print("Enter Bob's private key (XB) less than " + p + ": ");
        BigInteger y = new BigInteger(scanner.nextLine());
        BigInteger R2 = g.modPow(y, p); // Bob's Public Key (YB)
        System.out.println("Bob's Public Key (YB): " + R2);

        // 4. Exchange and Calculate Shared Secret
        // Alice calculates K = (YB)^XA mod p
        BigInteger k1 = R2.modPow(x, p);
        System.out.println("\nKey calculated at Alice's side: " + k1);

        // Bob calculates K = (YA)^XB mod p
        BigInteger k2 = R1.modPow(y, p);
        System.out.println("Key calculated at Bob's side:   " + k2);

        // Verify keys match
        if (!k1.equals(k2)) {
            System.out.println("Error: Keys do not match! Check your math.");
            return;
        }

        System.out.println("\n--- Secure Communication Established ---");
        System.out.println("Shared Secret Key: " + k1);

        // 5. Encrypt/Decrypt using the Shared Key
        System.out.print("\nEnter a message to send securely: ");
        String message = scanner.nextLine();

        // Encrypt
        String encryptedMessage = encrypt(message, k1);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt
        String decryptedMessage = decrypt(encryptedMessage, k2); // Bob uses his copy of the key
        System.out.println("Decrypted Message: " + decryptedMessage);

        scanner.close();
    }
}