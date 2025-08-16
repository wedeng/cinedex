package entity;

import java.time.LocalDateTime;

/**
 * Represents a user session in the application.
 * Stores the session token and user ID for the current user.
 */
public class Session implements SessionInterface {
    private static final int TIME = 24;
    private String sessionId;
    private int accountId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    /**
     * Default constructor.
     */
    public Session() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusHours(TIME);
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
        this.expiresAt = this.createdAt.plusHours(TIME);
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public int getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiresAt);
    }
}
