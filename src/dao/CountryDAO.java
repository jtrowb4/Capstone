package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * class CountryDAO.java
 *
 * @author James Trowbridge
 *
 */
public class CountryDAO {

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /**
     * displayAllCountries gets all countries
     * @return allCountries
     */
    public static ObservableList<Country> displayAllCountries() throws SQLException, Exception{

        if (allCountries.size() > 0){
            allCountries.clear();
        }
        DBConnector.openConnection();
        String CountryQuery = "SELECT * FROM client_schedule.countries";
        DBQuery.makeQuery(CountryQuery);
        ResultSet result = DBQuery.getResult();
        while(result.next()){
            int countryID = result.getInt("Country_ID");
            String countryName = result.getString("Country");
            allCountries.add(new Country(countryID, countryName));
        }
        DBConnector.closeConnection();
        return allCountries;
    }

}
