package core.keys;

import java.math.BigInteger;

/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class PrivateKey extends PublicKey{
    public BigInteger phi;
    public BigInteger mu;

    public PrivateKey(BigInteger p, BigInteger q) {
        super(p,q);
        generatesKey(p,q);
    }

    public PublicKey getPublicKey (){
        return new PublicKey(n,nsquare,g);
    }

    @Override
    public void generatesKey(BigInteger p, BigInteger q){
        super.generatesKey(p,q);
        this.phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        this.mu = g.modPow(phi, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
    }
}
