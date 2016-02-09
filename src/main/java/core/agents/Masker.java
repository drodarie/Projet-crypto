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
        for (int i = 0; i < responses.size(); i++) {
            result.add(maskResponse(I, BigInteger.valueOf(i + 1), responses.get(i), publicKey));
        }
        return result;
    }

    public BigInteger maskResponse(BigInteger encryptNumberOfQuestion, BigInteger indexToMask,
                                   BigInteger responseToMask, BigInteger publicKey) throws Exception {
        BigInteger r = new BigInteger(64,new Random()).mod(publicKey);
        BigInteger nsquare = publicKey.multiply(publicKey);

        BigInteger encryptI = Paillier.encrypt(publicKey.subtract(indexToMask), publicKey);
        BigInteger mask = (encryptNumberOfQuestion.multiply(encryptI)).modPow(r, nsquare);
        mask = mask.multiply(Paillier.encrypt(responseToMask,publicKey));
        return mask;
    }
}
