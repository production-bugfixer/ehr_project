package ehr.encrypt.aes;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecryptMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T decryptToObject(String encryptedBody, Class<T> clazz) {
        try {
            String json = AESDecryptor.decrypt(encryptedBody);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Decryption or mapping failed", e);
        }
    }
}
