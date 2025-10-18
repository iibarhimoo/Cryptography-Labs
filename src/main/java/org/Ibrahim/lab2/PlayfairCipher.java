package org.Ibrahim.lab2;

import java.awt.Point;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.HashMap;

public class PlayfairCipher {

    private String[][] table;
    private HashMap<Character, Point> locationMap;

    // main method to run the program
    public static void main(String args[]) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("--- Playfair Cipher Encryption & Decryption ---");

            // Get key from user
            System.out.print("Enter the key for Playfair cipher: ");
            String key = sc.nextLine();
            while (key.trim().isEmpty()) {
                System.out.println("Key cannot be empty. Please enter a valid key:");
                key = sc.nextLine();
            }

            // Create a PlayfairCipher object
            PlayfairCipher playfair = new PlayfairCipher(key);
            playfair.keyTable();

            // Get plaintext from user
            System.out.print("Enter the plaintext to be enciphered: ");
            String plaintext = sc.nextLine();
            while (plaintext.trim().isEmpty()) {
                System.out.println("Plaintext cannot be empty. Please enter a valid message:");
                plaintext = sc.nextLine();
            }

            // Encrypt and decrypt the message
            String encryptedMessage = playfair.encrypt(plaintext);
            String decryptedMessage = playfair.decrypt(encryptedMessage);

            // Print the results
            playfair.printResults(encryptedMessage, decryptedMessage);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Constructor to set up the cipher table and location map
    private PlayfairCipher(String key) {
        this.table = new String[5][5];
        this.locationMap = new HashMap<>();
        createCipherTable(key);
    }

    // Prepares the plaintext before encryption by handling duplicates and padding
    private String preparePlaintext(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');

        StringBuilder preparedText = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char firstChar = text.charAt(i);
            preparedText.append(firstChar);

            if (i + 1 < text.length()) {
                char secondChar = text.charAt(i + 1);
                if (firstChar == secondChar) {
                    preparedText.append('X');
                    i++;
                } else {
                    preparedText.append(secondChar);
                    i += 2;
                }
            } else {
                i++;
            }
        }

        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }
        return preparedText.toString();
    }

    // Creates the 5x5 cipher table and the location map for efficient lookup
    private void createCipherTable(String key) {
        LinkedHashSet<Character> keySet = new LinkedHashSet<>();
        key = key.toUpperCase().replace('J', 'I');
        for (char c : key.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                keySet.add(c);
            }
        }

        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        for (char c : alphabet.toCharArray()) {
            keySet.add(c);
        }

        int row = 0, col = 0;
        for (char c : keySet) {
            table[row][col] = String.valueOf(c);
            locationMap.put(c, new Point(row, col));
            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }
    }

    // Encrypts the prepared plaintext
    public String encrypt(String plaintext) {
        String preparedText = preparePlaintext(plaintext);
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < preparedText.length(); i += 2) {
            char a = preparedText.charAt(i);
            char b = preparedText.charAt(i + 1);

            Point ptA = locationMap.get(a);
            Point ptB = locationMap.get(b);

            int r1 = ptA.x, c1 = ptA.y;
            int r2 = ptB.x, c2 = ptB.y;

            if (r1 == r2) { // Same row
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            } else if (c1 == c2) { // Same column
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            } else { // Rectangle rule
                int tempC = c1;
                c1 = c2;
                c2 = tempC;
            }

            cipherText.append(table[r1][c1]).append(table[r2][c2]);
        }
        return cipherText.toString();
    }

    // Decrypts the ciphertext
    public String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);

            Point ptA = locationMap.get(a);
            Point ptB = locationMap.get(b);

            int r1 = ptA.x, c1 = ptA.y;
            int r2 = ptB.x, c2 = ptB.y;

            if (r1 == r2) { // Same row
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            } else if (c1 == c2) { // Same column
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            } else { // Rectangle rule
                int tempC = c1;
                c1 = c2;
                c2 = tempC;
            }

            decryptedText.append(table[r1][c1]).append(table[r2][c2]);
        }
        return decryptedText.toString();
    }

    // Prints the cipher key table
    private void keyTable() {
        System.out.println("\nPlayfair Cipher Key Matrix:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Prints the final results
    private void printResults(String encrypted, String decrypted) {
        System.out.println("\n--- Results ---");
        System.out.println("Encrypted Message: " + encrypted);
        System.out.println("Decrypted Message: " + decrypted);
    }
}