package com.zeenat.zeemart.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private PasswordUtil() {}

    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean matches(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}
