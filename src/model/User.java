package model;
/**
 * class User.java
 *
 * @author James Trowbridge
 */
public class User extends Employee {

    // class related to the User Table
    private String password;

    /**
     * Constructor for User
     */
    public User (int userID, String userName, String password) {
        super(userID, userName);
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    /**
     * @param password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}