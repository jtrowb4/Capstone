package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class Customer.java
 *
 * @author James Trowbridge
 */

public class Customer {
    //This class will have information and methods related to the Customer table
    private ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String divisionName;

    public Customer (int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    public Customer (int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * @param customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @param customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * @return the divisionID
     */
    public int getDivisionID() {return divisionID;
    }

    /**
     * @param divisionID to set
     */
    public void setDivisionID(int divisionID) {;
        this.divisionID = divisionID;
    }

    /**
     * @return the divisionID
     */
    public String getDivisionName(){

        return divisionName;
    }
    /**
     * @param divisionName to set
     */
    public void setDivisionName(String divisionName){

        this.divisionName = divisionName;
    }

    /**
     * @param appointment associated appointment to add
     * function to add Appointment object to ObservableList associatedAppointment
     */
    public void addAssociatedAppointment(Appointment appointment){
        associatedAppointments.add(appointment);

    }
    /**
     * @param appointment associated appointment to delete
     * @return boolean on whether to delete associatedAppointment from ObservableList
     */
    public boolean deleteAssociatedAppointment(Appointment appointment){
        return associatedAppointments.remove(appointment);
    }
    /**
     * @return the all associatedAppointment from ObservableList
     */
    public ObservableList<Appointment> getAllAssociatedAppointment(){
        return associatedAppointments;
    }

    @Override
    public String toString(){
        return (customerName);
    }
}
