package model;
/**
 * class Contact.java
 *
 * @author James Trowbridge
 */
public class Contact extends Employee{

    private String email;

    public Contact(int contactID, String contactName, String email) {
        super(contactID, contactName);
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
