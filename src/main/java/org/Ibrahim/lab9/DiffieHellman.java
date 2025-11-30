package org.Ibrahim.lab9;

import java.io.*;
import java.math.BigInteger;

// *** SYNTAX ERROR FIXED *** (Removed space in class name)
class DiffieHellman {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter prime number:");
        BigInteger p = new BigInteger(br.readLine());

        System.out.print("Enter primitive root of " + p + ":");
        BigInteger g = new BigInteger(br.readLine());

        System.out.println("Enter value for XA (Alice's private key) less than " + p + ":");
        BigInteger x = new BigInteger(br.readLine());
        // Calculate Alice's public key
        BigInteger R1 = g.modPow(x, p);
        System.out.println("Public key for Alice YA = " + R1);

        System.out.print("Enter value for XB (Bob's private key) less than " + p + ":");
        BigInteger y = new BigInteger(br.readLine());
        // Calculate Bob's public key
        BigInteger R2 = g.modPow(y, p);
        System.out.println("Public key For BOB YB = " + R2);

        // Alice calculates the shared secret
        BigInteger k1 = R2.modPow(x, p);
        System.out.println("Key calculated at Alice's side:" + k1);

        // Bob calculates the shared secret
        BigInteger k2 = R1.modPow(y, p);
        System.out.println("Key calculated at Bob's side:" + k2);

        System.out.println("Diffie-Hellman secret key exchange successful.");
    }
}
