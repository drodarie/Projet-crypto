package core.keys;

import core.agents.Alice;
import core.agents.Bob;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Pierre on 09/02/2016.
 */
public class PaillierTest {

    @Test
    public void testGenerateKeys() throws Exception {
        Keys generatedKeys = Paillier.generateKeys();
        assertTrue(generatedKeys.getPublicKey().compareTo(BigInteger.ZERO) > 0);
        assertTrue(generatedKeys.getSecretKey().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        Keys generatedKeys = Paillier.generateKeys();

        BigInteger value = BigInteger.valueOf(1);
        BigInteger result = Paillier.encrypt(value, generatedKeys.getPublicKey());
        result = Paillier.decrypt(result, generatedKeys.getPublicKey(), generatedKeys.getSecretKey());
        System.out.println(result.toString());
        assertEquals("testEncryptDecrypt test encrypt/decrypt : it should be BigInteger.ONE", result, BigInteger.ONE);
    }

    @Test
    public void testMaskOnePositiveValue() throws  Exception{
        Keys generatedKeys = Paillier.generateKeys();

        BigInteger value = BigInteger.valueOf(2);
        BigInteger result = Paillier.encrypt(value, generatedKeys.getPublicKey());
        Alice alice = new Alice(new ArrayList<BigInteger>(),generatedKeys.getPublicKey());
        result = alice.maskResponse(result, BigInteger.valueOf(2), BigInteger.valueOf(0), generatedKeys.getPublicKey());
        result = Paillier.decrypt(result, generatedKeys.getPublicKey(), generatedKeys.getSecretKey());
        assertEquals("testEncryptDecrypt test encrypt/decrypt : it should be BigInteger.ZERO", BigInteger.ZERO, result);
    }

    @Test
    public void testMaskValues() throws Exception{
        Keys generatedKeys = Paillier.generateKeys();
        ArrayList<BigInteger> responses = new ArrayList<>();
        //Pour faire simple on va dire que les réponses sont 1, 2, 3 jusqu'à 10
        for (int i = 1; i <= 10; i++) {
            responses.add(BigInteger.valueOf(i));
        }

        Alice alice = new Alice(responses, generatedKeys.getPublicKey());
        BigInteger X = Paillier.encrypt(BigInteger.valueOf(2), generatedKeys.getPublicKey());
        ArrayList<BigInteger> Mx = alice.maskResponses(X, generatedKeys.getPublicKey());

        for (int i = 0; i < 10; i++) {
            BigInteger temp = Paillier.decrypt(Mx.get(i), generatedKeys.getPublicKey(), generatedKeys.getSecretKey());
            if(i == 1){
                assertEquals("", BigInteger.valueOf(2), temp);
            }
            else {
                assertNotEquals("", BigInteger.valueOf(i), temp);
            }
        }
    }


    @Test
    public void testMain() throws Exception{
        ArrayList<BigInteger> responses = new ArrayList<>();
        //Pour faire simple on va dire que les réponses sont 1, 2, 3 jusqu'à 10
        for (int i = 0; i < 10; i++) {
            responses.add(BigInteger.valueOf(i));
        }

        Bob bob = new Bob();
        Alice alice = new Alice(responses,bob.getPublicKey());

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