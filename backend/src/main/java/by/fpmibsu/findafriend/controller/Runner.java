package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.DbAnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.photo.DbPhotoDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.DbPlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.user.DbUserDao;
import by.fpmibsu.findafriend.entity.*;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

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