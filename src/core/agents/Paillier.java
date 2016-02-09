package core.agents;

import core.keys.PrivateKey;
import core.keys.PublicKey;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class Paillier {
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public Paillier(){
        generateKeys();
    }

    public Paillier(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    public void generateKeys() {
        BigInteger p = generatePrimal(64),
                q = generatePrimal(64);
        privateKey = new PrivateKey(p,q);
        publicKey = privateKey.getPublicKey();
    }

    public BigInteger encrypt(BigInteger m) throws Exception {
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(publicKey.n) >= 0)
            throw new Exception("Paillier.encrypt : m is not in Z_n");

        BigInteger r = new BigInteger(64,new Random()).mod(publicKey.n);
        return (publicKey.g.modPow(m, publicKey.nsquare).multiply(r.modPow(publicKey.n, publicKey.nsquare))).mod(publicKey.nsquare);
    }

    public BigInteger decrypt(BigInteger c) throws Exception {
        if (privateKey==null)
            throw new Exception("Paillier.decrypt : private key unknown or undeclared");
        else if (c.compareTo(BigInteger.ZERO) < 0 || c.compareTo(publicKey.nsquare) >= 0 || c.gcd(publicKey.nsquare).intValue() != 1)
            throw new Exception("Paillier.decrypt : c is not in Z*_{n^2}");
        return c.modPow(privateKey.phi, publicKey.nsquare).subtract(BigInteger.ONE).divide(publicKey.n).multiply(privateKey.mu).mod(publicKey.n);
    }

    public static boolean isPrimalFermat (BigInteger p){
        return BigInteger.valueOf(2).modPow(p.subtract(BigInteger.ONE),p).equals(BigInteger.ONE);
    }

    public static BigInteger generatePrimal (int nbBits){
        BigInteger result;
        do {
            result = new BigInteger(nbBits,new Random());
        }while (!isPrimalFermat(result));
        return result;
    }
}
