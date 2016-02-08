import java.math.BigInteger;
import java.util.Random;

/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        BigInteger p = generatePrimal(64),
                q = generatePrimal(64),
                n = p.multiply(q),
                phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)),
                x = new BigInteger(64,new Random()).mod(n),
                r = new BigInteger(64, new Random()).mod(n);
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
