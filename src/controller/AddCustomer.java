package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * class AddCustomer.java
 *
 * @author James Trowbridge
 *
 */
public class AddCustomer implements Initializable {
    public TextField customerIDText;
    public TextField customerAddressText;
    public TextField postalText;
    public TextField nameFirstText;
    public TextField nameLastText;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> stateCombo;
    public Button saveButton;
    public Button cancelButton;
    public TextField phoneText;

    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Loaded Add Customer");
        try {
            countryCombo.setItems(CountryDAO.displayAllCountries());
            countryCombo.setVisibleRowCount(5);
            stateCombo.setItems(FirstLevelDivisionDAO.displayAllDivisions());
            stateCombo.setVisibleRowCount(5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent cancel button action - closes AddCustomer form.
     * @throws IOException scene will not load or other exception
     */
    public void cancel(ActionEvent actionEvent) throws Exception {
        //confirm cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Cancel Creating New Customer?");
        alert.setContentText("Press OK to Exit. Press Cancel to continue editing Customer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            CustomerDAO.displayAllCustomers();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        }
    }
    /**
     * @param actionEvent save button action
     *                    creates and saves new customer object
     *                    customer is then added to the DB and displayed in CustomerMenu
     * @throws IOException scene will not load or other exception
     */
    public void save(ActionEvent actionEvent) throws IOException {
        // save  things. First it creates a new customer. Second it loads back to the DB.
        //Validate fields for New Customer
        ObservableList<Customer> saveCustomer = FXCollections.observableArrayList();

        try {

            //check for empty first
            if ((nameFirstText.getText().isBlank() || nameFirstText.getText().isEmpty()) ||
                    (nameLastText.getText().isBlank() || nameLastText.getText().isEmpty()) ||
                    (customerAddressText.getText().isBlank() || customerAddressText.getText().isEmpty()) ||
                    (phoneText.getText().isBlank() || phoneText.getText().isEmpty()) ||
                    (postalText.getText().isBlank() || postalText.getText().isEmpty())) {
                throw new Exception("Input Required: All fields must contain valid inputs.");
            }

            //get customer info
            int customerID = 0;
            String customerName = nameFirstText.getText() + " " + nameLastText.getText();
            String customerAddress  = customerAddressText.getText();
            String postalCode = postalText.getText();
            String phoneNumber = phoneText.getText();
            int firstLevel = stateCombo.getValue().getDivisionID();

            //Validate Entries
            Pattern specialChar = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);

            if (specialChar.matcher(customerName).find()) {
                throw new Exception("Input Exception: Customer Name must not contain special characters such as !@#$%? etc.");
            }
            if (specialChar.matcher(customerAddress).find()) {
                throw new Exception("Input Exception: Address must not contain special characters such as !@#$%? etc.");
            }
            if (specialChar.matcher(postalCode).find()) {
                throw new Exception("Input Exception: Postal Code must not contain special characters such as !@#$%? etc.");
            }
            else if (postalCode.length() > 5){
                throw new Exception("Input Exception: Postal Code must not exceed five digits");
            }

            if (specialChar.matcher(phoneNumber).find()) {
                throw new Exception("Input Exception: Phone Number must not contain special characters such as !@#$%? etc.");
            }
            else if (phoneNumber.length() != 10){
                throw new Exception("Input Exception: Phone Number must contain 10 digits.");
            }

            //Create Object
               Customer customer = new Customer(customerID, customerName, customerAddress,
                        postalCode, phoneNumber, firstLevel);
            //Save Local
                saveCustomer.add(customer);


            // Confirm Saving Object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Save New Customer?");
            alert.setContentText("Press OK to save Customer: " + customerName + ". Press Cancel to continue editing Customer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                CustomerDAO.insertCustomer(saveCustomer.get(0));
                CustomerDAO.displayAllCustomers();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

            } else {
                //cancel was selected and object removed from temp list. Continue Editing.
                saveCustomer.remove(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Code 400: Bad Request.");
            alert.setHeaderText("Exception has occurred.");
            alert.setContentText("Please verify all fields were entered correctly.\n" + e.getMessage());

            alert.showAndWait();
        }

    }
    /**
     * @param actionEvent cancel button action - uses country to display division.
     * @throws IOException scene will not load or other exception
     */
    public void countrySelection(ActionEvent actionEvent) throws Exception {
        int countryInt = countryCombo.getSelectionModel().getSelectedIndex();
        stateCombo.setItems(FirstLevelDivisionDAO.displayDivisions(countryInt + 1));
    }
}
