package core.agents;

import core.keys.PublicKey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Dimitri RODARIE on 09/02/2016.
 * @version 1.0
 */
public class Masker extends Paillier{
    private ArrayList<BigInteger> responses;

    public Masker(ArrayList<BigInteger> responses, PublicKey publicKey){
        super(publicKey);
        this.responses = responses;
    }

    public ArrayList<BigInteger> maskResponses (BigInteger I) throws Exception {
        ArrayList<BigInteger> result = new ArrayList<>();
        for (int i = 1; i <= responses.size(); i++) {
            BigInteger r = new BigInteger(64,new Random()).mod(publicKey.n),
                    maskI = (I.multiply(encrypt(publicKey.n.subtract(BigInteger.valueOf(i))))).modPow(r,publicKey.nsquare);
            //TODO: CODE 1 -> A supprimer et remplacer par le CODE 2 si il marche (test)
            //seul un masque i une fois décrypté sera égal à 0 si tout ce passe bien
            result.add(maskI);
            //TODO: CODE 2 -> A décommenter si le CODE1 marche
            //result.add(responses.get(i-1).multiply(maskI));
        }
        return result;
    }
}
