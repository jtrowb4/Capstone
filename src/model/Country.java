package model;
/**
 * class Country.java
 *
 * @author James Trowbridge
 */

public class Country {

    private String countryName;
    private int countryID;

    public Country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }
    /**
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * @param countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /**
     * override toString
     * @return the countryName
     */
    @Override
    public String toString(){
        return (countryName);
    }
}
