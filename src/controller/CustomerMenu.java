package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.collections.FXCollections;
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
 * class CustomerMenu.java
 *
 * @author James Trowbridge
 *
 */
public class CustomerMenu implements Initializable {

    public TableView customerTable;
    public TableColumn customerID;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerDivision;
    public TableColumn customerPostal;
    public TableColumn customerPhone;
    public TextField customerSearch;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button backButton;
    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivision.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        try {
            customerTable.setItems(CustomerDAO.displayAllCustomers());

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
        /**
         * load mainMenu scene
         */
        public void toMainMenu (ActionEvent actionEvent) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("QAM2_JavaApplication");
            stage.setScene(scene);
            stage.show();
        }
        /**
         *
         * @param actionEvent
         * @throws IOException
         * load Add Customer Menu
         */
        public void loadAddCustomer (ActionEvent actionEvent) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            Stage stage = new Stage(); //(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 450);
            stage.setTitle("QAM2_JavaApplication: Add Customer");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
        /**
         *
         * @param actionEvent
         * @throws IOException
         * load Modify Customer Menu
         */
        public void loadModifyCustomer (ActionEvent actionEvent) throws IOException {

            try {
                Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomer.fxml"));
                Parent root = loader.load();

                ModifyCustomer modifyCustomer = loader.getController();
                modifyCustomer.loadSelectedCustomer(selectedCustomer);

                Stage stage = new Stage(); //(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 600, 450);
                stage.setTitle("QAM2_JavaApplication: Modify Customer");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Code 400: Bad Request.");
                alert.setHeaderText("Exception has occurred.");
                alert.setContentText("Customer was not selected. Please select Customer to Modify.");

                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /**
         *
         * @param actionEvent
         * @throws IOException
         * delete customer
         */
        public void loadDeleteCustomer (ActionEvent actionEvent) throws Exception {
            Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

            ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
            int size = customerAppts.size();
            customerAppts = AppointmentDAO.getAppointment(selectedCustomer.getCustomerID());
            size = customerAppts.size();

            if (selectedCustomer != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Delete Customer?");
                alert.setContentText("Press OK to Delete Customer: " + selectedCustomer.getCustomerName() + ". Press Cancel to close without deleting.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    if (customerAppts.size() > 0)
                    {
                        Alert apptConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                        apptConfirm.setTitle("Confirm");
                        apptConfirm.setHeaderText("Appointments exist for " + selectedCustomer.getCustomerName() + ".");
                        apptConfirm.setContentText("Press OK to delete appointments and customer. Press Cancel to close without deleting.");

                        Optional<ButtonType> apptResult = apptConfirm.showAndWait();
                        if (apptResult.get() == ButtonType.OK)
                        {
                            for (Appointment appt: customerAppts)
                            {
                                selectedCustomer.getAllAssociatedAppointment().remove(appt);
                                AppointmentDAO.deleteAppointment(appt);
                                Alert informApptDelete = new Alert(Alert.AlertType.INFORMATION);
                                informApptDelete.setTitle("Success Code 200: Success.");
                                informApptDelete.setHeaderText("Processed Successfully.");
                                informApptDelete.setContentText("Appointment ID# " + appt.getAppointmentID() + " - " + appt.getType() + " for "
                                        + selectedCustomer.getCustomerName() + " at " + appt.getStartTime() + " has been deleted");
                                informApptDelete.show();
                            }

                            Customer tempCustomer = selectedCustomer;
                            CustomerDAO.deleteCustomer(selectedCustomer);
                            Alert inform = new Alert(Alert.AlertType.INFORMATION);
                            inform.setTitle("Success Code 200: Success.");
                            inform.setHeaderText("Processed Successfully.");
                            inform.setContentText("Customer: " + tempCustomer.getCustomerName() + "(" + selectedCustomer.getCustomerID() + ")" + " has been deleted");
                            inform.show();

                        }
                    }
                }
            }

            customerTable.setItems(CustomerDAO.displayAllCustomers());
        }
        /**
         *
         * @param actionEvent
         * @throws IOException
         * search customer function for tableview
         */
        public void searchCustomer (ActionEvent actionEvent) throws Exception {
            String searchText = customerSearch.getText();

            if (searchText == "") {
                customerTable.setItems(CustomerDAO.displayAllCustomers());
            } else {
                customerTable.setItems(CustomerDAO.findCustomer(searchText));
            }

            try {
                int customerID = Integer.parseInt(searchText);
                customerTable.setItems(CustomerDAO.findCustomer(customerID));

            } catch (NumberFormatException e) {
                //ignore
            }

            customerSearch.setText("");

        }
    }

