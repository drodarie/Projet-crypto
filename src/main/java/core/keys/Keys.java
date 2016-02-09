package core.keys;

import java.math.BigInteger;

public class Keys {
    private BigInteger publicKey;
    private BigInteger secretKey;

    public Keys(BigInteger publicKey, BigInteger secretKey) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getSecretKey() {
        return secretKey;
    }
}
