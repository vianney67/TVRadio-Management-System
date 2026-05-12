package service.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages active user sessions and OTPs.
 */
public class SessionManager {
    private static SessionManager instance;
    // Map stores custom model.Session objects (User Login Sessions), NOT Hibernate
    // Sessions
    private Map<String, model.Session> activeSessions; // SessionId -> Session

    // OTP storage: UserID -> OTP (In memory)
    private Map<String, String> otpStorage;

    private SessionManager() {
        activeSessions = new ConcurrentHashMap<String, model.Session>();
        otpStorage = new ConcurrentHashMap<String, String>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void addSession(model.Session session) {
        if (session != null && session.getSessionId() != null) {
            activeSessions.put((String) session.getSessionId(), session);
        }
    }

    public void removeSession(String sessionId) {
        if (sessionId != null) {
            activeSessions.remove(sessionId);
        }
    }

    public model.Session getSession(String sessionId) {
        if (sessionId == null)
            return null;
        return activeSessions.get(sessionId);
    }

    public void storeOTP(String username, String otp) {
        if (username != null && otp != null) {
            otpStorage.put(username, otp);
        }
    }

    public String getOTP(String username) {
        if (username == null)
            return null;
        return otpStorage.get(username);
    }

    public void clearOTP(String username) {
        if (username != null) {
            otpStorage.remove(username);
        }
    }
}
