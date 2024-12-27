package com.lty.bookstore.system.backend.constant;

public class RedisKeyConstant {

    public static final String USER_CAPTCHA_FREQ_REQUENCY = "user:captcha_freq_expire:phone_%s";

    public static final Long USER_TOKEN_TIMEOUT = 7L;

    public static String getKey(String key, Object... s) {
        return String.format(key, s);
    }
}
