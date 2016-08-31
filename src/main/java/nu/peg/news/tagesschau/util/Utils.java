package nu.peg.news.tagesschau.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utils {

    public static String sha256(String input) {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] inputBytes = input.getBytes(Charset.forName("UTF-8"));
        byte[] digest = sha256.digest(inputBytes);

        // Use BigInt to convert to Hex String
        BigInteger digestInteger = new BigInteger(1, digest);
        return digestInteger.toString(16);
    }

}
