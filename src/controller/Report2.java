package controller;

import dao.AppointmentDAO;

import dao.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Report2 implements Initializable {



    public ComboBox contactIDCombo;
    public Button submitButton;
    public TableView apptTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptCustomer;
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
            contactIDCombo.setItems(ContactDAO.displayAllContacts());

            apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
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
            String contactID = contactIDCombo.getValue().toString();

            apptTable.setItems(AppointmentDAO.getAppointment(contactID));
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Code 400: Bad Request.");
            alert.setHeaderText("Exception has occurred.");
            alert.setContentText("Contact was not selected. Please select an individual from the Contact dropdown.");

            alert.showAndWait();
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }


    }


}
