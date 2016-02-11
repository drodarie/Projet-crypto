package core.agents;

import core.keys.Paillier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Alice implements ProcessResponse{
    private ArrayList<BigInteger> responses;

    public Alice(ArrayList<BigInteger> responses, BigInteger publicKey){
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

    public void setResponses(ArrayList<BigInteger> responses) {
        this.responses = responses;
    }

    //Trnasformer la réponse qui arrive sous forme de strings en une réponse à renvoyer sous forme de string
    @Override
    public String process(String messageToRespond) {
        try {
            System.out.println("Message from Bob : " + messageToRespond);
            String[] message = messageToRespond.split(" ");
            BigInteger publicKey = new BigInteger(message[0]);
            BigInteger I = new BigInteger(message[1]);
            ArrayList<BigInteger> response = maskResponses(I, publicKey);
            StringBuilder responseToBuild = new StringBuilder("");
            for (int i = 0; i < response.size(); i++) {
                responseToBuild.append(response.get(i).toString());
                responseToBuild.append(" ");
            }

            System.out.println("Response to send : " + responseToBuild.toString());
            return responseToBuild.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
