package model;
/**
 * class FirstLevelDivision.java
 *
 * @author James Trowbridge
 */

public class FirstLevelDivision {
    //related to state and prov codes in the FLD table
    private int divisionID;
    private String divisionName;
    private int countryID;

    public FirstLevelDivision(int divisionID, String divisionName){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * @param divisionID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }
    /**
     * @param divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    /**
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * override toString
     * @return the divisionName
     */
    @Override
    public String toString(){
        return (divisionName);
    }
}
