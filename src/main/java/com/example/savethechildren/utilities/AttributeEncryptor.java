package com.example.savethechildren.utilities;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;

/**
 * This is an attribute converter class that will be applied as a converter at column level on the Donor DB Entity class.
 * It provides the code for the encryption and decryption of the entity attributes.
 */

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final String SECRET = "secret-key-12345";

    private final Key key;
    private final Cipher cipher;

    public AttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes("UTF-8"), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            if (attribute != null) {
            	return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
            }
            else {
            	return null;
            }
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            if (dbData != null) {
            	return new String(cipher.doFinal(Base64.getDecoder().decode(dbData.getBytes("UTF-8"))));
            }
            else {
            	return null;
            }
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        } 
    }
}
