package core.keys;

import java.math.BigInteger;
import java.util.Random;

public class Paillier {
    public static Keys generateKeys(){
        BigInteger p = generatePrimal(64),
                   q = generatePrimal(64);

        //n = p * q
        BigInteger n = p.multiply(q);
        BigInteger publicKey = n;

        //phi = (p + 1)(q + 1)
        BigInteger pun = p.add(BigInteger.ONE);
        BigInteger qun = q.add(BigInteger.ONE);
        BigInteger phi = pun.multiply(qun);

        BigInteger secretKey = phi;

        return new Keys(publicKey, secretKey);
    }

    public static BigInteger encrypt(BigInteger message, BigInteger publicKey) throws Exception {
        //if (message.compareTo(BigInteger.ZERO) < 0 || message.compareTo(publicKey) >= 0)
        //    throw new Exception("Paillier.encrypt : m is not in Z_n");

        BigInteger r = new BigInteger(64,new Random()).mod(publicKey);

        //g = n + 1
        BigInteger g = publicKey.add(BigInteger.ONE);
        BigInteger nsquare = publicKey.multiply(publicKey);

        //result = ((g^message) * (r^publicKey)) modulo nsquare
        BigInteger result =  (g.modPow(message, nsquare).multiply(r.modPow(publicKey, nsquare))).mod(nsquare);

        return result;
    }

    public static BigInteger decrypt(BigInteger messageToDecrypt, BigInteger publicKey, BigInteger secretKey) throws Exception {
        // nsquare = n * n
        BigInteger nsquare = publicKey.multiply(publicKey);

        //if (messageToDecrypt.compareTo(BigInteger.ZERO) < 0 || messageToDecrypt.compareTo(nsquare) >= 0 || messageToDecrypt.gcd(nsquare).intValue() != 1)
        //    throw new Exception("Paillier.decrypt : c is not in Z*_{n^2}");

        //lu
        BigInteger lu = publicKey.modPow(secretKey, nsquare);
        lu = lu.subtract(BigInteger.ONE).divide(publicKey);

        // mu
        BigInteger mu = secretKey.modPow(BigInteger.ONE.negate(), publicKey);

        BigInteger result = (lu.multiply(mu)).mod(publicKey);

        return result;
    }



    private static boolean isPrimalFermat (BigInteger p){
        return BigInteger.valueOf(2).modPow(p.subtract(BigInteger.ONE),p).equals(BigInteger.ONE);
    }

    private static BigInteger generatePrimal (int nbBits){
        BigInteger result;
        do {
            result = new BigInteger(nbBits,new Random());
        }while (!isPrimalFermat(result));
        return result;
    }


}
