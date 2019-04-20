import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Elgamal {
    public static void main(String[] args) {
        // Key Generation
        Random random = new SecureRandom();
        BigInteger secretKey = new BigInteger("123456789987654321");
        BigInteger p = BigInteger.probablePrime(64, random);
        BigInteger g = new BigInteger("3");
        BigInteger y = g.modPow(secretKey, p);

        // public key (q,g,y)
        // Sign
        String message = "4564468461684516161651686";

        BigInteger r = new BigInteger("0");
        BigInteger s = new BigInteger("0");
        BigInteger m, k;

        m = new BigInteger(message);
        k = BigInteger.probablePrime(64, random);
        r = g.modPow(k, p);
        s = ((new BigInteger(m.hashCode() + "")).subtract(secretKey.multiply(r)))
                .multiply(k.modInverse(p.subtract(new BigInteger("1"))));

        // Normal Verification
        BigInteger normal_rhs, normal_lhs;
        long normal_start = System.nanoTime();

        normal_rhs = g.modPow(new BigInteger((new BigInteger("123456")).hashCode() + ""), p);
        normal_lhs = y.modPow(r, p).multiply(r.modPow(s, p)).mod(p);
        if (normal_rhs.equals(normal_lhs)) {
            System.out.println("Sign " + " : true");
        } else {
            System.out.println("Sign " + " : false");
        }

    }
}