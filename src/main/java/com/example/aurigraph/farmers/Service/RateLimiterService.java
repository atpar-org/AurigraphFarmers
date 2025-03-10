package com.example.aurigraph.farmers.Service;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.Bandwidth;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RateLimiterService {
    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String phoneNumber) {
        return buckets.computeIfAbsent(phoneNumber, key -> createNewBucket());
    }

    private Bucket createNewBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(5)))) // Max 3 OTPs per 5 mins
                .build();
    }
}
