package org.Ibrahim.lab4;
import java.util.*;
import java.io.*;


class GFG
{

    // This function generates the key in
    // a cyclic manner until its length is
    // equal to the length of original text
    static String generateKey(String str, String key)
    {
        int x = str.length();

        for (int i = 0; ; i++)
        {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }

    // This function returns the encrypted text
    // generated with the help of the key
    static String cipherText(String str, String key)
    {
        String cipher_text="";

        for (int i = 0; i < str.length(); i++)
        {
            // converting in range 0-25
            // This is the Vigenere encryption formula: C = (P + K) mod 26
            // Note: This simple formula only works if the letters are already 0-25.
            // The code is combining steps. Let's fix the logic to be clearer.

            // 1. Convert plaintext char to 0-25
            int p = (str.charAt(i) - 'A');
            // 2. Convert key char to 0-25
            int k = (key.charAt(i) - 'A');

            // 3. Apply formula
            int x = (p + k) % 26;

            // 4. convert back into alphabets(ASCII)
            x += 'A';

            cipher_text+=(char)(x);
        }
        return cipher_text;
    }

    // This function decrypts the encrypted text
    // and returns the original text
    static String originalText(String cipher_text, String key)
    {
        String orig_text="";

        for (int i = 0 ; i < cipher_text.length() &&
                i < key.length(); i++)
        {
            // 1. Convert ciphertext char to 0-25
            int c = (cipher_text.charAt(i) - 'A');
            // 2. Convert key char to 0-25
            int k = (key.charAt(i) - 'A');

            // 3. This is the Vigenere decryption formula: P = (C - K + 26) mod 26
            int x = (c - k + 26) % 26;

            // 4. convert back into alphabets(ASCII)
            x += 'A';
            orig_text+=(char)(x);
        }
        return orig_text;
    }

    // This function will convert the lower case character to Upper case
    // and remove spaces
    static String LowerToUpper(String s)
    {
        // Remove all non-alphabetic characters (like spaces)
        String cleaned = s.replaceAll("[^a-zA-Z]", "");

        // Convert to uppercase
        return cleaned.toUpperCase();
    }

    // Driver code
    public static void main(String[] args)
    {
        // 1. Use only ONE scanner for all input
        Scanner scanner = new Scanner(System.in);

        // 2. Prompt for and read the plaintext
        System.out.print("Enter plaintext: ");
        String plainTextInput = scanner.nextLine();

        // 3. Prompt for and read the key
        System.out.print("Enter key: ");
        String keywordInput = scanner.nextLine();

        // 4. Close the scanner
        scanner.close();

        // 5. Now, pass the input STRINGS to your methods
        // LowerToUpper also removes spaces now
        String str = LowerToUpper(plainTextInput);
        String keyword = LowerToUpper(keywordInput);

        if (str.isEmpty() || keyword.isEmpty()) {
            System.out.println("Plaintext or key is empty after cleaning. Exiting.");
            return;
        }

        // The rest of your code works perfectly
        String key = generateKey(str, keyword);
        String cipher_text = cipherText(str, key);

        System.out.println("\nPlaintext (Cleaned): " + str);
        System.out.println("Key (Generated)  : " + key);
        System.out.println("Ciphertext : "
                + cipher_text + "\n");

        System.out.println("Decrypted Text : "
                + originalText(cipher_text, key));
    }
}
