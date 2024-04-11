package com.healthcare.system.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, String> session = new HashMap<>();

    public static boolean isAuthenticated(String sessionId) {
        return session.containsKey(sessionId);
    }

    public static String getEmail(String sessionId) {
        return session.get(sessionId);
    }


    public static String generateSessionId(String email) {
        String sessionId = UUID.randomUUID().toString();
        session.put(sessionId,email);
        return sessionId;
    }

    public static void removeSessionId(String sessionId) {
        session.remove(sessionId);
    }

}
