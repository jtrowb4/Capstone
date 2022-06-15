package model;
/**
 * class Employee.java
 *
 * @author James Trowbridge
 */
public class Employee {

    private int employeeID;
    private String employeeName;

    public Employee(int employeeID, String employeeName) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
    }
    /**
     * @return the employeeID
     */
    public int getEmployeeID() {
        return employeeID;
    }
    /**
     * @param employeeID to set
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }
    /**
     * @param employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    /**
     * override toString
     * @return the employeeName
     */
    @Override
    public String toString(){
        return (employeeName);
    }
}
