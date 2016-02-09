import core.agents.Masker;
import core.agents.Bob;

import java.math.BigInteger;
import java.util.ArrayList;


/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<BigInteger> responses = new ArrayList<>();
        //Pour faire simple on va dire que les réponses sont 1, 2, 3 jusqu'à 10
        for (int i = 0; i < 10; i++) {
            responses.add(BigInteger.valueOf(i));
        }

        Bob bob = new Bob();
        Masker alice = new Masker(responses,bob.getPublicKey());

        try {
            System.out.println("===== Début échange =====");
            BigInteger X = bob.encryptValue(BigInteger.valueOf(5));
            System.out.println("Encryption de 5 : " + X);
            ArrayList<BigInteger> Mx = alice.maskResponses(X, bob.getPublicKey());
            System.out.println("Encryption des masques : " + Mx);
            BigInteger x = bob.decryptMessage(Mx.get(4));
            System.out.println("Décryptage du masque : " + x);
            System.out.println("===== Fin échange =====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
