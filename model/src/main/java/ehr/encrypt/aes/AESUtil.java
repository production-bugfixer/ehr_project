package ehr.encrypt.aes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Base64;

public class AESUtil {
    private static final String SECRET_KEY = "1234567890123456";
    private static final String INIT_VECTOR = "RandomInitVector";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            throw new RuntimeException("Error encrypting", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static Object encryptRecursively(Object value) {
        if (value == null) {
            return null;
        } else if (isPrimitiveOrWrapper(value) || value instanceof String || value instanceof Enum) {
            return encrypt(value.toString());
        } else if (value instanceof Map) {
            Map<Object, Object> result = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                Object encryptedKey = encrypt(entry.getKey().toString());
                Object encryptedValue = encryptRecursively(entry.getValue());
                result.put(encryptedKey, encryptedValue);
            }
            return result;
        } else if (value instanceof Collection) {
            Collection<?> original = (Collection<?>) value;
            Collection<Object> encrypted = value instanceof List ? new ArrayList<>() : new HashSet<>();
            for (Object item : original) {
                encrypted.add(encryptRecursively(item));
            }
            return encrypted;
        } else if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            Object[] encryptedArray = new Object[length];
            for (int i = 0; i < length; i++) {
                encryptedArray[i] = encryptRecursively(Array.get(value, i));
            }
            return Arrays.asList(encryptedArray);
        } else {
            // Custom object
            Map<String, Object> map = mapper.convertValue(value, new TypeReference<>() {});
            Map<String, Object> encryptedMap = (Map<String, Object>) encryptRecursively(map);
            return encryptedMap;
        }
    }

    public static <T> T encryptObjectFieldsAndReturnSameObject(T data, Class<T> clazz) {
        try {
            Object encrypted = encryptRecursively(data);
            return mapper.convertValue(encrypted, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting object fields recursively", e);
        }
    }

    private static boolean isPrimitiveOrWrapper(Object value) {
        return value instanceof Integer || value instanceof Long || value instanceof Boolean ||
               value instanceof Double || value instanceof Float || value instanceof Short ||
               value instanceof Byte || value instanceof Character;
    }
}
