package com.serverktpm.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.serverktpm.exception.ServiceException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    @Value("${twilio.account.sid}")
    private String TWILIO_ACCOUNT_SID;
    @Value("${twilio.auth.token}")
    private String TWILIO_AUTH_TOKEN;
    @Value("${twilio.phone.number}")
    private String phoneFrom;
    private static final Integer EXPIRE_MIN = 2;
    private LoadingCache<String, Integer> otpCache;

    public OTPService() {
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public boolean sendOTP(String phoneNumber) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message.creator(new PhoneNumber(phoneNumber),
                new PhoneNumber(phoneFrom), "Your opt register account " + generateOTP(phoneNumber)).create();
        return true;
    }


    private int generateOTP(String phone) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(phone, otp);
        return otp;
    }

    public boolean validOtp(String phone, int otp) {
        try {
            Integer otpRoot = otpCache.get(phone);
            if (otpRoot != null && otpRoot.equals(otp)) {
                clearOTP(phone);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void clearOTP(String key) {
        otpCache.invalidate(key);
    }

}
