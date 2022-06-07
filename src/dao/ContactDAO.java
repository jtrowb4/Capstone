package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * class ContactDAO.java
 *
 * @author James Trowbridge
 *
 */
public class ContactDAO {

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    /**
     * displayAllContacts gets all contacts
     * @return allContacts
     */
    public static ObservableList<Contact> displayAllContacts() throws SQLException, Exception{

        if (allContacts.size() > 0){
            allContacts.clear();
        }

        DBConnector.openConnection();
        String searchQuery = "SELECT * FROM client_schedule.contacts";
        DBQuery.makeQuery(searchQuery);
        ResultSet result = DBQuery.getResult();
        while(result.next()){
            int contactID = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");
            allContacts.add(new Contact(contactID, contactName));
        }
        DBConnector.closeConnection();
        return allContacts;
    }
}

