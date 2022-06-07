package model;
/**
 * class Contact.java
 *
 * @author James Trowbridge
 */
public class Contact {

    private int contactID;
    private String contactName;

    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }
    /**
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }
    /**
     * @param contactID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * @param contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * override toString
     * @return the contactName
     */
    @Override
    public String toString(){
        return (contactName);
    }
}
