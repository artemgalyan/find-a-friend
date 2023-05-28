package by.fpmibsu.findafriend.controller;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Runner {
    public static void main(String[] args) {
        RsaJsonWebKey keyPair;
        var utils = new RsaKeyUtil();
        try {
            keyPair = RsaJwkGenerator.generateJwk(2048);
        } catch (JoseException e) {
            return;
        }
        try {
            var stream = new ObjectOutputStream(new FileOutputStream("private"));
            stream.writeObject(keyPair.getRsaPrivateKey());
            var stream2 = new ObjectOutputStream(new FileOutputStream("public"));
            stream2.writeObject(keyPair.getRsaPublicKey());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}