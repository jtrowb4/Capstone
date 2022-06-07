package model;
/**
 * class User.java
 *
 * @author James Trowbridge
 */
public class User {

    // class related to the User Table
    private int userID;
    private String userName;
    private String password;

    /**
     * Constructor for User
     */
    public User (int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }
    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }
    /**
     * @param userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return (userName);
    }
}