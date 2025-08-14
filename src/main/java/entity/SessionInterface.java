package entity;

import java.time.LocalDateTime;

public interface SessionInterface {

    /**
     * Returns Session id.
     * @return session id
     */
    String getSessionId();

    /**
     * Sets Session id.
     * @param sessionId the session id.
     */
    void setSessionId(String sessionId);

    /**
     * Gets the account id.
     * @return account id.
     */
    int getAccountId();

    /**
     * Sets the account id.
     * @param accountId account id.
     */
    void setAccountId(int accountId);

    /**
     * Gets the created time.
     * @return the date.
     */
    LocalDateTime getCreatedAt();

    /**
     * Sets the created time.
     * @param createdAt the date.
     */
    void setCreatedAt(LocalDateTime createdAt);

    /**
     * Gets the expired date.
     * @return the date.
     */
    LocalDateTime getExpiresAt();

    /**
     * Sets the expires at time.
     * @param expiresAt the date.
     */
    void setExpiresAt(LocalDateTime expiresAt);

    /**
     * Checks if the session is still valid.
     * @return true if the session hasn't expired
     */
    boolean isValid();
}
