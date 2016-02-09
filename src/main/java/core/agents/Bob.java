package core.agents;

import core.keys.Keys;
import core.keys.Paillier;

import java.math.BigInteger;
import java.util.Random;

public class Bob {
    private Keys keys;

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
}