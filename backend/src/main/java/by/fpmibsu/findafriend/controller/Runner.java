package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.DbAnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.photo.DbPhotoDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.DbPlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.user.DbUserDao;
import by.fpmibsu.findafriend.entity.*;
import by.fpmibsu.findafriend.services.HashPasswordHasher;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;

import java.io.*;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) {
        test();
        if (true) {
            return;
        }
        var properties = new Properties();
        try {
            properties.load(new FileReader("config/config.properties"));
        } catch (IOException exception) {
            System.err.println("Failed to start the app, cannot read config");
            exception.printStackTrace();
            return;
        }
        var dbPath = (String) properties.getProperty("database_url");
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPath);
            var user = new DbUserDao(connection).getAll().get(3);
            var place = new DbPlaceDao(connection).getAll().get(0);
            var advert = new AnimalAdvert(1,  "title", "descr", "cat", new ArrayList<>(), user, Date.from(Instant.now()), place, Date.from(Instant.now()), AnimalAdvert.Sex.MALE, false);
            new DbAnimalAdvertDao(connection).create(advert);
            var photo = new Photo("what a cool text".getBytes(), 16);
            new DbPhotoDao(connection).create(photo);

        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to closed db connection");
            return;
        }
    }

    private static void test() {
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
//        try {
//            var s = new ObjectInputStream(new FileInputStream("keys"));
//            var o = (KeyPair) s.readObject();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}