package ehr.encrypt.aes;

import java.util.Set;

public class EncryptableTypes {

    private static final Set<Class<?>> ENCRYPTABLE_TYPES = Set.of(
        String.class, Integer.class, Long.class, Double.class,
        Float.class, Short.class, Byte.class, Character.class
    );

    public static boolean isEncryptableField(Class<?> clazz) {
        return ENCRYPTABLE_TYPES.contains(clazz);
    }
}

