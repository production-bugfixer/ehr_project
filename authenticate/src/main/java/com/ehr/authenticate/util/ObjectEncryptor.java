package com.ehr.authenticate.util;

import com.ehr.authenticate.dto.GlobalEncryptedResponse;
import com.ehr.authenticate.dto.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ObjectEncryptor {

    private static final String SECRET_KEY = "1234567890123456"; // Must be 16 chars
    private static final String INIT_VECTOR = "6543210987654321"; // Must be 16 chars

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> GlobalEncryptedResponse encryptFields(GlobalResponse<T> response) {
        try {
            // Step 1: Convert response object to JSON string
            String json = mapper.writeValueAsString(response);

            // Step 2: Encrypt the full JSON string
            String encrypted = encrypt(json);

            // Step 3: Wrap encrypted string in a GlobalEncryptedResponse
            GlobalEncryptedResponse encryptedResponse = new GlobalEncryptedResponse();
            encryptedResponse.setData(encrypted);
            encryptedResponse.setLanguage(response.getLanguage()); // optional
            return encryptedResponse;

        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt response object", e);
        }
    }

    private static String encrypt(String plainText) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            throw new RuntimeException("Error while encrypting", ex);
        }
    }
}

