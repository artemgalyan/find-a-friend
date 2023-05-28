package by.fpmibsu.findafriend.application.authentication;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

import java.security.Key;

public class JwtSigner {
    private final Key privateKey;

    public JwtSigner(Key key) {
        this.privateKey = key;
    }

    public String signJwt(String jsonClaims) throws JoseException {
        var jws = new JsonWebSignature();
        jws.setPayload(jsonClaims);
        jws.setKey(privateKey);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        return jws.getCompactSerialization();
    }
}
