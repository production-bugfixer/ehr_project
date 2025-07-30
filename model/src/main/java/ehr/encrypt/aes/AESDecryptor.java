package ehr.encrypt.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class AESDecryptor {

    private static final String SECRET_KEY = "1234567890123456";  // Must be 16 characters (128-bit)
    private static final String INIT_VECTOR = "6543210987654321"; // Must be 16 characters

    public static String decrypt(String encryptedBase64) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // PKCS5Padding is Java's name for PKCS7
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedBase64);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}

