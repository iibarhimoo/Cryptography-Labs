package org.Ibrahim.lab10;

import java.io.*;
import java.math.*;
import java.util.Scanner;

class RSA {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int p, q, n, pn, publickey = 0, d = 0;

        System.out.println("ENTER Two Prime Numbers (p and q):");
        p = in.nextInt();
        q = in.nextInt();

        if (!isPrime(p) || !isPrime(q)) {
            System.out.println("One or both numbers are not prime.");
            return;
        }

        n = p * q;
        pn = (p - 1) * (q - 1);

        // Find public key 'e'
        for (int e = 2; e < pn; e++) {
            if (gcd(e, pn) == 1) {
                publickey = e;
                System.out.println("PUBLIC KEY (e): " + e);
                break;
            }
        }

        // Find private key 'd'
        for (int i = 0; i < pn; i++) {
            d = i;
            if (((d * publickey) % pn) == 1) break;
        }
        System.out.println("PRIVATE KEY (d): " + d);

        System.out.print("ENTER MESSAGE (Integer): ");
        int msg = in.nextInt();

        // Encryption: C = M^e mod n
        // Using BigInteger for correct modular exponentiation
        BigInteger msgBig = BigInteger.valueOf(msg);
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger eBig = BigInteger.valueOf(publickey);
        BigInteger dBig = BigInteger.valueOf(d);

        BigInteger cipher = msgBig.modPow(eBig, nBig);
        System.out.println("ENCRYPTED Message: " + cipher);

        // Decryption: M = C^d mod n
        BigInteger ptext = cipher.modPow(dBig, nBig);
        System.out.println("DECRYPTED Message: " + ptext);

        in.close();
    }

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}