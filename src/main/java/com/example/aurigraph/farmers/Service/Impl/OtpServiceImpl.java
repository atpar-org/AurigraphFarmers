package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Service.OtpService;
import com.example.aurigraph.farmers.Service.RateLimiterService;
import com.example.aurigraph.farmers.Service.TwilioService;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private RateLimiterService rateLimiterService;



    private static final int OTP_EXPIRATION = 5; // OTP expires in 5 minutes

    @Override
    public String generateOtp(String phoneNumber) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // Generate 6-digit OTP
        redisTemplate.opsForValue().set(phoneNumber, otp, OTP_EXPIRATION, TimeUnit.MINUTES);
        return otp;
    }

    @Override
    public boolean validateOtp(String phoneNumber, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(phoneNumber);
        return storedOtp.equals(otp);
    }
    @Override
    public String sendOtpSms(String phoneNumber) {
        Bucket bucket = rateLimiterService.resolveBucket(phoneNumber);
        if (!bucket.tryConsume(1)) {
            return "Too many requests! Please try again later.";
        }
        String otp = generateOtp(phoneNumber);
        String message = String.format(otp + " is your verification code. For your security, do not share this code.");
        return twilioService.sendSms(phoneNumber, message);
    }
}
