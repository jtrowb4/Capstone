package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class UserDAO.java
 *
 * @author James Trowbridge
 */
public class UserDAO {
    // create an interface that holds methods such as Create, Read, Update, and Delete.
    // define those methods in the classes you create for user, customer, contact, appointment, etc.
    /**
     * getUserLogin gets User
     */
    public static User getUserLogin(String userNameText) throws SQLException, Exception{

        DBConnector.openConnection();
        String loginQuery = "SELECT * FROM client_schedule.users WHERE User_Name = '" + userNameText + "'";
        DBQuery.makeQuery(loginQuery);
        User userLogin;
        ResultSet result = DBQuery.getResult();
        while(result.next()){
            int userid = result.getInt("User_ID");
            String userName = result.getString("User_Name");
            String password = result.getString("Password");
            userLogin = new User(userid, userName, password);
            DBConnector.closeConnection();
            return userLogin;
        }
        DBConnector.closeConnection();
        return null;
    }

    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    /**
     * displayAllCountries gets all countries
     * @return allCountries
     */
    public static ObservableList<User> displayAllUsers() throws SQLException, Exception{

        if (allUsers.size() > 0){
            allUsers.clear();
        }
        DBConnector.openConnection();
        String UserQuery = "SELECT * FROM client_schedule.users";
        DBQuery.makeQuery(UserQuery);
        ResultSet result = DBQuery.getResult();
        while(result.next()){
            int userID = result.getInt("User_ID");
            String userName = result.getString("User_Name");
            String password = result.getString("Password");
            allUsers.add(new User(userID, userName, password));
        }
        DBConnector.closeConnection();
        return allUsers;
    }


}
