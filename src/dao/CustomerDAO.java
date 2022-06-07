package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class CustomerDAO.java
 *
 * @author James Trowbridge
 *
 */
public class CustomerDAO {

    private static String joinCall = "SELECT Customer_ID,Customer_Name,Address,Postal_Code,Phone,client_schedule.customers.Division_ID,Division " +
            "FROM client_schedule.customers, client_schedule.first_level_divisions " +
            "WHERE client_schedule.customers.Division_ID = client_schedule.first_level_divisions.Division_ID";

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /**
     * displayAllCustomers gets all customers for tableview
     * @return allCustomer
     */
    public static ObservableList<Customer> displayAllCustomers() throws SQLException, Exception {

        if (allCustomers.size() > 0){
            allCustomers.clear();
        }
        DBConnector.openConnection();
        String selectCustomerQuery = joinCall;

        DBQuery.makeQuery(selectCustomerQuery);
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int customerID = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int divisionID = result.getInt("Division_ID");
            String divisionName = result.getString("Division");
            addCustomer(new Customer(customerID, customerName, address, postalCode, phone, divisionID, divisionName));
        }
        DBConnector.closeConnection();
        return allCustomers;
    }
    /**
     * addCustomer adds customer to all customer list
     */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * insertCustomer used to add customer to DB
     */
    public static void insertCustomer(Customer customer) {
        DBConnector.openConnection();
        String insertCustomerQuery = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, Division_ID) VALUES('"
                + customer.getCustomerID() + "', '"
                + customer.getCustomerName() + "', '"
                + customer.getAddress() + "', '"
                + customer.getPostalCode() + "', '"
                + customer.getPhoneNumber() + "', '"
                + customer.getDivisionID() + "')";
        DBQuery.makeQuery(insertCustomerQuery);
        DBConnector.closeConnection();
    }
    /**
     * updateCustomer used to update customer record on DB
     */
    public static void updateCustomer(Customer customer) {
        DBConnector.openConnection();
        String updateCustomerQuery =
                "UPDATE client_schedule.customers " +
                "SET Customer_Name = '" + customer.getCustomerName() +
                 "', Address = '" + customer.getAddress() +
                 "', Postal_Code = '" + customer.getPostalCode() +
                 "', Phone = '" + customer.getPhoneNumber() +
                 "', Division_ID = '" + customer.getDivisionID() +
                     "' WHERE Customer_ID = '" + customer.getCustomerID() + "'";
        DBQuery.makeQuery(updateCustomerQuery);
        DBConnector.closeConnection();
    }
    /**
     * deleteCustomer used to delete customer record from DB
     */
    public static void deleteCustomer(Customer customer){
        DBConnector.openConnection();
        String deleteCustomerQuery = "DELETE FROM client_schedule.customers WHERE Customer_ID = '" + customer.getCustomerID() + "'";
        DBQuery.makeQuery(deleteCustomerQuery);
        DBConnector.closeConnection();
    }

    public static ObservableList<Customer> findCustomer(String searchText) throws SQLException {

        if (allCustomers.size() > 0){
        allCustomers.clear();
        }

        DBConnector.openConnection();
        String searchQuery = joinCall + " AND Customer_Name LIKE '%" + searchText + "%' ";
        DBQuery.makeQuery(searchQuery);
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
        int customerID = result.getInt("Customer_ID");
        String customerName = result.getString("Customer_Name");
        String address = result.getString("Address");
        String postalCode = result.getString("Postal_Code");
        String phone = result.getString("Phone");
        int division = result.getInt("Division_ID");
        String divisionName = result.getString("Division");
        addCustomer(new Customer(customerID, customerName, address, postalCode, phone, division, divisionName));
    }
        DBConnector.closeConnection();
        return allCustomers;
    }
    public static ObservableList<Customer> findCustomer(int searchText) throws SQLException {

        if (allCustomers.size() > 0){
            allCustomers.clear();
        }

        DBConnector.openConnection();
        String searchQuery = joinCall + " AND Customer_ID = '" + searchText + "'";
        DBQuery.makeQuery(searchQuery);
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int customerID = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            int division = result.getInt("Division_ID");
            String divisionName = result.getString("Division");
            addCustomer(new Customer(customerID, customerName, address, postalCode, phone, division, divisionName));
        }
        DBConnector.closeConnection();
        return allCustomers;
    }
}


