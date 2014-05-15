package main;

import java.math.BigInteger;

/**
 * Created by joubin on 5/15/14.
 */
public final class SecretShare {
    // Declare Variables
    private final int num;
    private final BigInteger share;

    // Constructor
    public SecretShare(int num, BigInteger share) {
        this.num = num;
        this.share = share;
    }


    public BigInteger getShare() {
        return share;
    }

    @Override
    public String toString() {
        return "SecretShare [Person: " + num + ", Share:" + share + "]";
    }
}