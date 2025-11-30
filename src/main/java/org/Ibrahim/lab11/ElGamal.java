package org.Ibrahim.lab11;

import java.util.*;
import java.math.BigInteger;

public class ElGamal {
    public static void main(String[] args) { // *** SYNTAX ERROR FIXED *** (Added [])
        Scanner stdin = new Scanner(System.in);
        Random r = new Random();

        // Get user input for p.
        System.out.println("Enter the approximate value of the prime number for your El Gamal key.");
        BigInteger p = getNextPrime(stdin.next());

        // Calculate a generator.
        BigInteger g = getGenerator(p, r);

        // We found a generator, so let's do the rest of it.
        if (g != null) {
            // Pick a secret a (Alice's private key)
            // *** LOGICAL ERROR FIXED *** (Used bitLength, not bitCount)
            BigInteger a = new BigInteger(p.bitLength() - 1, r);

            // Calculate the corresponding public b (Alice's public key)
            BigInteger b = g.modPow(a, p);

            // Print out our public keys.
            System.out.println("Post p = " + p + " g = " + g + " b = " + b);

            // When we send a message, the sender picks a random k.
            // *** LOGICAL ERROR FIXED *** (Used bitLength, not bitCount)
            BigInteger k = new BigInteger(p.bitLength() - 1, r);

            // Sender calculates C1
            BigInteger c1 = g.modPow(k, p);
            // Sender calculates K = b^k
            BigInteger K = b.modPow(k, p);

            // Here we get the message from the user.
            System.out.println("Please enter your message. It should be in between 1 and " + p);
            BigInteger m = new BigInteger(stdin.next());

            // Now, we can calculate C2 = K * m mod p
            BigInteger c2 = K.multiply(m).mod(p);

            // Print out the two ciphertexts.
            System.out.println("The corresponding cipher texts are c1 = " + c1 + " c2 = " + c2);

            // --- Decryption ---
            // First, determine K = C1^a mod p
            K = c1.modPow(a, p);
            // Calculate K_inverse = K^-1 mod p
            BigInteger K_inverse = K.modInverse(p);

            // Print this out.
            System.out.println("Here is K (c1^a) = " + K);
            System.out.println("Here is K_inverse = " + K_inverse);

            // Now, just multiply this by the second ciphertext
            // M = C2 * K^-1 mod p
            BigInteger recover = c2.multiply(K_inverse).mod(p);

            // And this will give us our original message back!
            System.out.println("The original message = " + recover);
        }
        // My sorry message!
        else {
            System.out.println("Sorry, a generator for your prime couldn't be found.");
        }
    }

    // Incrementally tries each BigInteger starting at the value passed
    // in as a parameter until one of them is tests as being prime.
    public static BigInteger getNextPrime(String ans) {
        BigInteger one = new BigInteger("1");
        BigInteger test = new BigInteger(ans);
        while (!test.isProbablePrime(99))
            test = test.add(one);
        return test;
    }

    // Postcondition - if a generator for p can be found, then it is returned
    // if no generator is found after 1000 tries, null is returned.
    public static BigInteger getGenerator(BigInteger p, Random r) {
        int numtries = 0;
        BigInteger p_minus_1 = p.subtract(BigInteger.ONE);

        // Try finding a generator at random 1000 times.
        while (numtries < 1000) {
            // Here's what we're trying as the generator this time.
            // *** LOGICAL ERROR FIXED *** (Used bitLength, not bitCount)
            BigInteger rand = new BigInteger(p.bitLength() - 1, r);

            // Simple check: g must be > 1
            if(rand.compareTo(BigInteger.ONE) <= 0) continue;

            BigInteger exp = BigInteger.ONE;
            BigInteger next = rand.mod(p);

            // We exponentiate our generator until we get 1 mod p.
            // This is an inefficient way to check for a generator,
            // but it matches the provided code's logic.
            while (!next.equals(BigInteger.ONE)) {
                next = (next.multiply(rand)).mod(p);
                exp = exp.add(BigInteger.ONE);
            }

            // If the first time we hit 1 is the exponent p-1, then we have a generator.
            if (exp.equals(p_minus_1))
                return rand;

            numtries++;
        }
        // None of the 1000 values we tried was a generator.
        return null;
    }
}
