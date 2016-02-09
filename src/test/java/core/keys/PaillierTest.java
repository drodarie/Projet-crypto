package core.keys;

import org.junit.Test;

import java.math.BigInteger;

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
        assertTrue(result.compareTo(value) == 0);
    }
}