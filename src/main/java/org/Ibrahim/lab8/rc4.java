package org.Ibrahim.lab8;

import java.io.*;

class rc4 {
    public static void main(String args[]) throws IOException {
        int temp = 0;
        String ptext;
        String key;
        int s[] = new int[256];
        int k[] = new int[256];

        // Use BufferedReader instead of deprecated DataInputStream
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nENTER PLAIN TEXT\t");
        ptext = in.readLine();
        System.out.print("\n\nENTER KEY TEXT\t\t");
        key = in.readLine();

        char ptextc[] = ptext.toCharArray();
        char keyc[] = key.toCharArray();
        int cipher[] = new int[ptext.length()];
        int decrypt[] = new int[ptext.length()];
        int ptexti[] = new int[ptext.length()];
        int keyi[] = new int[key.length()];

        for (int i = 0; i < ptext.length(); i++) {
            ptexti[i] = (int) ptextc[i];
        }
        for (int i = 0; i < key.length(); i++) {
            keyi[i] = (int) keyc[i];
        }

        // --- KSA (Key-Scheduling Algorithm) ---
        // *** LOGICAL ERROR FIXED *** (Loop must run to 256)
        for (int i = 0; i < 256; i++) {
            s[i] = i;
            k[i] = keyi[i % key.length()];
        }

        int j = 0;
        // *** LOGICAL ERROR FIXED *** (Loop must run to 256)
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + k[i]) % 256;
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        // --- PRGA (Pseudo-Random Generation Algorithm) ---
        int i = 0;
        j = 0;
        int z = 0;
        for (int l = 0; l < ptext.length(); l++) {
            // *** CRITICAL LOGIC FIXED ***
            // 'i' must be incremented from its previous state, not based on 'l'
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;

            temp = s[i];
            s[i] = s[j];
            s[j] = temp;

            z = s[(s[i] + s[j]) % 256];
            cipher[l] = z ^ ptexti[l];
            decrypt[l] = z ^ cipher[l]; // Decryption is the same XOR operation
        }

        System.out.print("\n\nENCRYPTED:\t\t");
        display(cipher);
        System.out.print("\n\nDECRYPTED:\t\t");
        display(decrypt);
        System.out.println(); // Added for cleaner output
    }

    static void display(int disp[]) {
        char convert[] = new char[disp.length];
        for (int l = 0; l < disp.length; l++) {
            convert[l] = (char) disp[l];
            System.out.print(convert[l]);
        }
    }
}