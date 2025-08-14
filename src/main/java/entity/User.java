package entity;

/**
 * The representation of a User for our program.
 */

public class User implements UserInterface {
    private int accountId;
    private String username;

    public User(int id, String username) {
        this.accountId = id;
        this.username = username;
    }

    @Override
    public int getAccountId() {
        return this.accountId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
