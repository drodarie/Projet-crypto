package core.agents;

import core.keys.Paillier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Masker{
    private ArrayList<BigInteger> responses;

    public Masker(ArrayList<BigInteger> responses, BigInteger publicKey){
        this.responses = responses;
    }

    public ArrayList<BigInteger> maskResponses (BigInteger I, BigInteger publicKey) throws Exception {
        ArrayList<BigInteger> result = new ArrayList<>();
        for (int i = 1; i <= responses.size(); i++) {
            result.add(maskResponse(I, i, publicKey));
        }
        return result;
    }

    private BigInteger maskResponse(BigInteger I, int i, BigInteger publicKey) throws Exception {
        BigInteger r = new BigInteger(64,new Random()).mod(publicKey);
        BigInteger nsquare = publicKey.multiply(publicKey);

        BigInteger encryptI = Paillier.encrypt(BigInteger.valueOf(-i), publicKey);
        BigInteger mask = (I.multiply(encryptI)).modPow(r, nsquare);

        return mask;
    }
}
