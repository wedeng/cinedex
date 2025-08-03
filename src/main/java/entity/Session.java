package entity;

import java.time.LocalDateTime;

/**
 * Represents a user session in the application.
 * Stores the session token and user ID for the current user.
 */
public class Session {
    private String sessionId;
    private int accountId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    /**
     * Default constructor.
     */
    public Session() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusHours(24); // Sessions expire in 24 hours
    }

    /**
     * Constructor with session details.
     * @param sessionId the TMDB session ID
     * @param accountId the user's account ID
     */
    public Session(String sessionId, int accountId) {
        this.sessionId = sessionId;
        this.accountId = accountId;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusHours(24); // Sessions expire in 24 hours
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Checks if the session is still valid.
     * @return true if the session hasn't expired
     */
    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiresAt);
    }
}
