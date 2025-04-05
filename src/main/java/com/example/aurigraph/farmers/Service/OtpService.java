package com.example.aurigraph.farmers.Service;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public interface OtpService {


    String generateOtp(String phoneNumber);

    boolean validateOtp(String phoneNumber, String otp);

    String sendOtpSms(String phoneNumber);
}
