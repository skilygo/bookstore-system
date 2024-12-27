package com.lty.bookstore.system.backend.application.service;

import com.lty.bookstore.system.backend.constant.RedisKeyConstant;
import com.lty.bookstore.system.backend.utils.RandomStringUtils;
import com.lty.bookstore.system.backend.utils.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CaptchaAppService {

    @Resource
    private RedisManager<String> redisManager;

    private final static int CAPTCHA_TIMEOUT = 5;
    private final static String BOOKING_ENGINE_CAPTCHA_KEY = "bookstore_system:sms_captcha:phone_%s";

    public void sendSmsCaptcha(String phone) {
        String captchaFreqExpireKey = RedisKeyConstant.getKey(RedisKeyConstant.USER_CAPTCHA_FREQ_REQUENCY, phone);
        long freqExpire = redisManager.getExpire(captchaFreqExpireKey);
        if (freqExpire > 0) {
            throw new IllegalStateException("The message send too frequent.Please try later.");
        }
        String verifyCode = RandomStringUtils.getRandomNumberStr(6);
        log.info("verify code:{}", verifyCode);
        String smsCaptchaKey = RedisKeyConstant.getKey(BOOKING_ENGINE_CAPTCHA_KEY, phone);
        redisManager.set(smsCaptchaKey, verifyCode, CAPTCHA_TIMEOUT * 60);
        redisManager.set(captchaFreqExpireKey, null, 60);
    }

    public boolean verifySmsCaptcha(String phone, String smsCaptcha) {
        String smsCaptchaKey = RedisKeyConstant.getKey(BOOKING_ENGINE_CAPTCHA_KEY, phone);
        Object smsCaptchaCache = redisManager.get(smsCaptchaKey);
        if (!smsCaptcha.equals(smsCaptchaCache)) {
            return false;
        }
        redisManager.del(smsCaptchaKey);
        return true;
    }
}
