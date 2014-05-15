package main;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;


public final class Shamir {
    // Declare Variables
    private BigInteger prime;
    private final int k;
    private final int n;
    private final Random random;
    private static final int CERTAINTY = 50;
    private  BigInteger secret = null;
    private SecretShare[] sharedSecret;

    // Cosnstructor
    public Shamir(final int k, final int n, final BigInteger secret) {
        this.k = k;
        this.n = n;
        this.secret = secret;
        random = new Random();
        this.prime = this.getPrime();
        this.split();

    }



    private SecretShare[] split() {
        final int modLength = secret.bitLength() + 1;
        
        // Generate Prime number for mod; ensures security
        prime = new BigInteger(modLength, CERTAINTY, random);
        System.out.println("Prime #: " + prime);
        
        // Create coefficient container for randomly selected numbers
        final BigInteger[] coeff = new BigInteger[k - 1];
        for (int i = 0; i < k - 1; i++) {
            coeff[i] = randomPrimeInteger(prime);
            System.out.println("a" + (i + 1) + ": " + coeff[i]);
        }

        // Create container to store secret shares once generated
        final SecretShare[] shares = new SecretShare[n];
        for (int i = 1; i <= n; i++) {
            BigInteger accum = secret;
            
            // individual secret shares = [ x, f(x) ]
            for (int j = 1; j < k; j++) {
                final BigInteger t1 = BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), prime);
                final BigInteger t2 = coeff[j - 1].multiply(t1).mod(prime);
                accum = accum.add(t2).mod(prime);
            }
            shares[i-1] = new SecretShare(i, accum);
        }
        this.sharedSecret = shares;
        return shares;
    }

    public SecretShare[] getSecrets(){
        return this.sharedSecret;
    }

    private BigInteger getPrime() {
        return prime;
    }

    public BigInteger combine(ArrayList shares) {

        BigInteger accum = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger num2 = BigInteger.ONE;
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    BigInteger first = BigInteger.valueOf(-j - 1);
                    BigInteger second = BigInteger.valueOf(i - j);


                    num = num.multiply(first).mod(this.prime);
                    num2 = num2.multiply(second).mod(this.prime);
                }
            }
            String shareString[] = ((String) shares.get(i)).split(":");
            System.out.println(shareString.toString());
            int personNum = Integer.parseInt(shareString[1].replaceAll("[^\\d.]", ""));
            int personSec = Integer.parseInt(shareString[2].replaceAll("[^\\d.]", ""));
            System.out.println("testing code number is "+ personNum + " and sec is " + personSec );
//            final BigInteger value = this.sharedSecret[i].getShare();
            final BigInteger value = BigInteger.valueOf(personSec);
            final BigInteger tmp = value.multiply(num).multiply(num2.modInverse(this.prime)).mod(this.prime);
            accum = accum.add(this.prime).add(tmp).mod(this.prime);
        }
        return accum;
    }

    private BigInteger randomPrimeInteger(final BigInteger p) {
        while (true) {
            final BigInteger rpi = new BigInteger(p.bitLength(), random);
            // Coeff must be between 0 < rpi < prime
            if (rpi.compareTo(BigInteger.ZERO) > 0 && rpi.compareTo(p) < 0) {
                return rpi;
            }
        }
    }
}