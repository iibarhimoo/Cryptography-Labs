package org.Ibrahim.lab10;

import java.io.*;
import java.math.BigInteger; // *** ADDED IMPORT ***

class RSA {
    public static void main(String args[]) throws IOException {
        int p, q, n, pn, publickey = 0, d = 0;
        int check, check1;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("ENTER Two Prime Numbers");
        p = Integer.parseInt(in.readLine());
        q = Integer.parseInt(in.readLine());

        check = prime(p);
        check1 = prime(q);

        if (check != 1 || check1 != 1) {
            System.out.println("Numbers are not prime. Exiting.");
            System.exit(0);
        }

        n = p * q;
        pn = (p - 1) * (q - 1); // Totient

        for (int e = 2; e < pn; e++) {
            // *** SYNTAX ERROR FIXED *** (==)
            if (gcd(e, pn) == 1) {
                publickey = e;
                System.out.println("PUBLIC KEY:" + e);
                break;
            }
        }

        // Brute-force find modular inverse 'd'
        for (int i = 0; i < pn; i++) {
            d = i;
            if (((d * publickey) % pn) == 1)
                break;
        }
        System.out.println("PRIVATE KEY :" + d);

        System.out.println("ENTER MESSAGE (as a number)");

        // *** CRITICAL LOGIC FIXED ***
        // Must use BigInteger for modPow, otherwise it will overflow.
        BigInteger msg = new BigInteger(in.readLine());
        BigInteger n_big = BigInteger.valueOf(n);
        BigInteger e_big = BigInteger.valueOf(publickey);
        BigInteger d_big = BigInteger.valueOf(d);

        // Encryption: C = M^e mod n
        BigInteger cipher = msg.modPow(e_big, n_big);
        System.out.println("ENCRYPTED Message:" + cipher);

        // Decryption: M = C^d mod n
        BigInteger ptext = cipher.modPow(d_big, n_big);
        System.out.println("DECRYPTED Message:" + ptext);
    }

    static int prime(int a) {
        int flag = 0;
        for (int i = 2; i <= a / 2; i++) { // Optimized loop
            if (a % i == 0) {
                System.out.println(a + " is not a Prime Number");
                flag = 1;
                return 0;
            }
        }
        // *** SYNTAX ERROR FIXED *** (==)
        if (flag == 0)
            return 1;
        return 1; // This line is reachable if prime check fails
    }

    static int gcd(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        }
        return gcd(number2, number1 % number2);
    }
}