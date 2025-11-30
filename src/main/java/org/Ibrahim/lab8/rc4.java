package org.Ibrahim.lab8;

import java.io.*;
import java.util.Scanner;

class RC4 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        System.out.print("ENTER PLAIN TEXT: ");
        String ptext = in.nextLine();
        System.out.print("ENTER KEY TEXT: ");
        String key = in.nextLine();

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

        int s[] = new int[256];
        int k[] = new int[256];

        // KSA (Key-Scheduling Algorithm)
        for (int i = 0; i < 256; i++) {
            s[i] = i;
            k[i] = keyi[i % key.length()];
        }

        int j = 0;
        int temp;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + k[i]) % 256;
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        // PRGA (Pseudo-Random Generation Algorithm)
        int i = 0;
        j = 0;
        int z;
        for (int l = 0; l < ptext.length(); l++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;

            z = s[(s[i] + s[j]) % 256];
            cipher[l] = z ^ ptexti[l];
            decrypt[l] = z ^ cipher[l];
        }

        System.out.print("ENCRYPTED: ");
        display(cipher);
        System.out.print("\nDECRYPTED: ");
        display(decrypt);
        System.out.println();
    }

    static void display(int disp[]) {
        for (int l = 0; l < disp.length; l++) {
            System.out.print((char) disp[l]);
        }
    }
}