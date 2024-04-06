package com.healthcare.system.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<Object, String> sessions = new HashMap<>();

    public static boolean isAuthenticated(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public static String getEmail(String sessionId) {
        return sessions.get(sessionId);
    }


    public static String generateSessionId(String email) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId,email);
        return sessionId;
    }

    public static void removeSessionId(String sessionId) {
        sessions.remove(sessionId);
    }
}
