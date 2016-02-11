package core.agents;

import core.keys.Keys;
import core.keys.Paillier;

import java.math.BigInteger;
import java.util.Random;

public class Bob {
    private Keys keys;
    private int numQuestion;

    public Bob() {
        this.keys = Paillier.generateKeys();
    }

    public BigInteger getPublicKey(){
        return keys.getPublicKey();
    }

    public BigInteger encryptValue(BigInteger value) throws Exception {
        return Paillier.encrypt(value, keys.getPublicKey());
    }

    public BigInteger decryptMessage(BigInteger message) throws Exception {
        return Paillier.decrypt(message, keys.getPublicKey(), keys.getSecretKey());
    }

    public String createMessageToSend(int numQuestion) throws Exception {
        this.numQuestion = numQuestion;
        BigInteger encodedQuestion = Paillier.encrypt(BigInteger.valueOf(numQuestion), getPublicKey());
        String result = getPublicKey().toString() + " " + encodedQuestion;
        return result;
    }

    public String decodeResponse(String response) throws Exception {
        System.out.println("Response from Alice : " + response);
        String[] responses = response.split(" ");
        BigInteger responseDecrypted = decryptMessage(new BigInteger(responses[numQuestion - 1]));
        return responseDecrypted.toString();
    }
}