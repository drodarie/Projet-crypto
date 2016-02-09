package core.keys;

import java.math.BigInteger;

/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class PublicKey {
    public BigInteger n;
    public BigInteger nsquare;
    public BigInteger g;

    public PublicKey (BigInteger p, BigInteger q){
        generatesKey(p,q);
    }

    public PublicKey(BigInteger n, BigInteger nsquare, BigInteger g){
        this.n = n;
        this.nsquare = nsquare;
        this.g = g;
    }

    public void generatesKey(BigInteger p, BigInteger q){
        n = p.multiply(q);
        nsquare = n.multiply(n);
        g = n.add(BigInteger.ONE);
        //Cas où p et q ne sont pas de même longueur
        /*do {
            g = new BigInteger(64, new Random()).mod(nsquare);
        }
        while (!g.equals(BigInteger.ZERO) && g.modPow(phi, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1);
        */
    }
}
