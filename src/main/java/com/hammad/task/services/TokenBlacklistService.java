package com.hammad.task.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TokenBlacklistService {

    private final ConcurrentMap<String, Date> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklistToken(String token, Date expirationTime) {
        blacklistedTokens.put(token, expirationTime);
        cleanupExpiredTokens();
    }

    public boolean isTokenBlacklisted(String token) {
        cleanupExpiredTokens();
        return blacklistedTokens.containsKey(token);
    }

    private void cleanupExpiredTokens() {
        Date now = new Date();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().before(now));
    }

    public int getBlacklistedTokenCount() {
        cleanupExpiredTokens();
        return blacklistedTokens.size();
    }
}