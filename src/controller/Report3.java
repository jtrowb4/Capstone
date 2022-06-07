package controller;

import dao.AppointmentDAO;

import dao.ContactDAO;

import dao.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Report3 implements Initializable {



    public ComboBox customerIDCombo;
    public Button submitButton;
    public TableView apptTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptType;
    public TableColumn apptStart;
    public TableColumn apptEnd;
    public TableColumn apptDescription;

    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try
        {
            customerIDCombo.setItems(CustomerDAO.displayAllCustomers());

            apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            apptEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent actionEvent) throws SQLException {
        try
        {
            Customer customer = (Customer) customerIDCombo.getSelectionModel().getSelectedItem();
            int customerID = customer.getCustomerID();

            apptTable.setItems(AppointmentDAO.getAppointment(customerID));
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Code 400: Bad Request.");
            alert.setHeaderText("Exception has occurred.");
            alert.setContentText("Customer was not selected. Please select an individual from the Customer dropdown.");

            alert.showAndWait();
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }


    }


}
