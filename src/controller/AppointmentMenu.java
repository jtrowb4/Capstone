package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * class AppointmentMenu.java
 *
 * @author James Trowbridge
 *
 */
public class AppointmentMenu implements Initializable {
    public Button backButton;
    public TableView apptTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptDescription;
    public TableColumn apptLocation;
    public TableColumn apptContact;
    public TableColumn apptType;
    public TableColumn apptStart;
    public TableColumn apptEnd;
    public ToggleGroup apptGroup;
    public RadioButton allApptRadio;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public RadioButton allWeekRadio;
    public RadioButton allMonthRadio;
    public TableColumn customerID;
    public TableColumn userID;

    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            apptTable.setItems(AppointmentDAO.displayAllAppointments());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load mainMenu scene
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("QAM2_JavaApplication");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * turn on radio button for Month view
     */
    public void monthApptOn(ActionEvent actionEvent) throws Exception {
        AppointmentDAO.displayThisMonthAppointments();
    }
    /**
     * turn on radio button for Week view
     */
    public void weekApptOn(ActionEvent actionEvent) throws Exception {
        AppointmentDAO.displayThisWeekAppointments();
    }
    /**
     * turn on radio button for All appointment view
     */
    public void allApptOn(ActionEvent actionEvent) throws Exception {

        AppointmentDAO.displayAllAppointments();
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     * load Add Appointment Menu
     */
    public void loadAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("QAM2_JavaApplication: Add Appointment");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    /**
     *
     * @param actionEvent
     * @throws IOException
     * load Modify Appointment Menu
     */
    public void loadModifyAppointment(ActionEvent actionEvent) throws IOException {
        try {
            Appointment selectedAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointment.fxml"));
            Parent root = loader.load();

            ModifyAppointment modifyAppointment = loader.getController();
            modifyAppointment.loadSelectedAppointment(selectedAppointment);

            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 450);
            stage.setTitle("QAM2_JavaApplication: Modify Appointment");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Code 400: Bad Request.");
            alert.setHeaderText("Exception has occurred.");
            alert.setContentText("Appointment was not selected. Please select Appointment to Modify.");

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @param actionEvent
     * @throws IOException
     * delete appointment
     */
    public void loadDeleteAppointment(ActionEvent actionEvent) throws Exception {
        Appointment selectedAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Delete Appointment?");
            alert.setContentText("Press OK to Delete - \n\nAppointment ID#: " + selectedAppointment.getAppointmentID() + "\nType: "
                    + selectedAppointment.getType() + "\nTime: " + selectedAppointment.getStartTime() + "\n\nPress Cancel to close without deleting.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                AppointmentDAO.deleteAppointment(selectedAppointment);
                ObservableList<Customer> customers = CustomerDAO.findCustomer(((Appointment) apptTable.getSelectionModel().getSelectedItem()).getCustomerID());
                if(customers != null)
                {
                    customers.get(0).deleteAssociatedAppointment(selectedAppointment);
                    System.out.println(customers.get(0).getAllAssociatedAppointment().toString());
                }

                Alert informApptDelete = new Alert(Alert.AlertType.INFORMATION);
                informApptDelete.setTitle("Success Code 200: Success.");
                informApptDelete.setHeaderText("Processed Successfully.");
                informApptDelete.setContentText("Appointment ID#: " + selectedAppointment.getAppointmentID() + " - " + selectedAppointment.getType() + " at " + selectedAppointment.getStartTime() + " has been deleted");
                informApptDelete.show();
            }

        }
        allApptRadio.setSelected(true);
        apptTable.setItems(AppointmentDAO.displayAllAppointments());
    }
}
