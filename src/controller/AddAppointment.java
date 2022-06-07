package controller;

import dao.*;
import interfaces.CalcInterface;
import interfaces.StringCreate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * class AddAppointment.java
 *
 * @author James Trowbridge
 *
 */
public class AddAppointment implements Initializable {

    public Button saveButton;
    public Button cancelButton;
    public TextField apptIDText;
    public TextField titleText;
    public TextField descriptionText;
    public ComboBox<Customer> customerNameCombo;
    public ComboBox<Contact> contactCombo;
    public ComboBox<String> apptTypeCombo;
    public DatePicker datePicker;
    public TextField locationText;
    public static ObservableList<LocalTime> appointments = FXCollections.observableArrayList();
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<User> userNameCombo;

    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Loaded Add Appointment");
        try {
            userNameCombo.setItems(UserDAO.displayAllUsers());
            customerNameCombo.setItems(CustomerDAO.displayAllCustomers());
            customerNameCombo.setVisibleRowCount(5);
            contactCombo.setItems(ContactDAO.displayAllContacts());
            contactCombo.setVisibleRowCount(5);
            ObservableList<String> apptTypes = FXCollections.observableArrayList();
            apptTypes.add(0,"Planning Session");
            apptTypes.add(1,"De-Briefing");
            apptTypes.add(2,"Coffee ");
            apptTypes.add(3,"Business Meeting");
            apptTypeCombo.setItems(apptTypes);
            startTimeCombo.setItems(listAppointmentTimes());

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
        alert.setHeaderText("Cancel Creating New Appointment?");
        alert.setContentText("Press OK to Exit. Press Cancel to continue editing Appointment.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            AppointmentDAO.displayAllAppointments();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        }
    }
    /**
     * @param actionEvent save button action
     *                    creates and saves new appointment object
     *                    appointment is then added to the DB and displayed in appointment menu
     * @throws IOException scene will not load or other exception
     */
    public void save(ActionEvent actionEvent) throws IOException {
        // save  things. First it creates a new customer. Second it loads back to the DB.
        //Validate fields for New Appointment
        ObservableList<Appointment> saveAppointment = FXCollections.observableArrayList();

        try {

            //check for empty first
            if ((titleText.getText().isBlank() || titleText.getText().isEmpty()) ||
                    (descriptionText.getText().isBlank() || descriptionText.getText().isEmpty())) {
                throw new Exception("Input Required: All fields must contain valid inputs.");
            }

            //get customer info
            int apptID = 0;
            String apptTitle = titleText.getText();
            String apptDescription = descriptionText.getText();
            String location = locationText.getText();
            String customerName = customerNameCombo.getValue().toString();
            int contact = contactCombo.getValue().getContactID();
            String apptType = apptTypeCombo.getValue();
            int userID = userNameCombo.getSelectionModel().getSelectedItem().getUserID();

            //set date & time
            LocalDate date = datePicker.getValue();
            int startHour = startTimeCombo.getValue().getHour();
            int startMin = startTimeCombo.getValue().getMinute();
            LocalTime start = LocalTime.of(startHour, startMin, 0);
            int timeIndex = startTimeCombo.getSelectionModel().getSelectedIndex();
            startTimeCombo.getSelectionModel().select(timeIndex + 1);
            int endHour = startTimeCombo.getValue().getHour();
            int endMin = startTimeCombo.getValue().getMinute();
            LocalTime end = LocalTime.of(endHour, endMin, 0);
            startTimeCombo.getSelectionModel().select(timeIndex);
            LocalDateTime localDateStartTime = LocalDateTime.of(date, start);
            LocalDateTime localDateEndTime = LocalDateTime.of(date, end);
            ZoneId localZoneID = ZoneId.systemDefault();
            ZonedDateTime localZDTstart = ZonedDateTime.of(localDateStartTime, localZoneID);
            ZonedDateTime localZDTend = ZonedDateTime.of(localDateEndTime, localZoneID);
            ZoneId utcZoneID = ZoneId.of("UTC");
            ZonedDateTime storedDateStartTime = ZonedDateTime.ofInstant(localZDTstart.toInstant(), utcZoneID);
            ZonedDateTime storedDateEndTime = ZonedDateTime.ofInstant(localZDTend.toInstant(), utcZoneID);
            ZoneId estZoneID = ZoneId.of("America/New_York");
            ZonedDateTime businessZDTStart = ZonedDateTime.ofInstant(storedDateStartTime.toInstant(), estZoneID);
            ZonedDateTime businessZDTEnd = ZonedDateTime.ofInstant(storedDateEndTime.toInstant(), estZoneID);

            //Check against business hours
            LocalTime businessOpen = LocalTime.of(8, 0);
            LocalTime businessClose = LocalTime.of(22, 0);
            if (businessZDTStart.toLocalTime().getHour() < (businessOpen.getHour()) || businessZDTEnd.toLocalTime().getHour() > (businessClose.getHour())) {
                throw new Exception("Input Exception: Time selected is outside of Business hours.\n Select time between 8:00(EST) and 22:00(EST).");
            }
            else {
                ObservableList<Appointment> appointments = AppointmentDAO.displayAllAppointments();
                for (Appointment appointment : appointments) {
                    LocalDate thisDay = storedDateStartTime.toLocalDate();
                    int hour = storedDateStartTime.toLocalTime().getHour();
                    int min = storedDateStartTime.toLocalTime().getMinute();
                    LocalTime thisTime = LocalTime.of(hour, min, 0);
                    LocalDateTime thisApptLDT = LocalDateTime.of(thisDay, thisTime);
                    String otherDay = appointment.getStartTime(); //grabbing local
                    String[] daySplit = otherDay.split(" ");
                    LocalDate otherDate = LocalDate.parse(daySplit[0]);
                    LocalTime otherTime = LocalTime.parse(daySplit[1]);
                    LocalDateTime apptLDT = LocalDateTime.of(otherDate, otherTime);
                    ZonedDateTime apptZoneLocal= ZonedDateTime.of(apptLDT, localZoneID);
                    ZonedDateTime apptZoneUTC= ZonedDateTime.ofInstant(apptZoneLocal.toInstant(), utcZoneID);

                    if (thisApptLDT.equals(apptZoneUTC.toLocalDateTime())) {
                        throw new Exception("Input Exception: Time selected overlaps with other appointments. Please select a different time");
                    }
                }
            }

            //Validate Entries
            Pattern specialChar = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);

            if (specialChar.matcher(apptTitle).find()) {
                throw new Exception("Input Exception: Title must not contain special characters such as !@#$%? etc.");
            }
            if (specialChar.matcher(apptDescription).find()) {
                throw new Exception("Input Exception: Description must not contain special characters such as !@#$%? etc.");
            }
            /**
             *
             * Lambda used to make new String
             */
            StringCreate stringCreate = (s1, s2) -> s1 + " " + s2;
            String formattedStart = stringCreate.Combine(storedDateStartTime.toLocalDate().toString(), storedDateStartTime.toLocalTime().toString());
            String formattedEnd = stringCreate.Combine(storedDateEndTime.toLocalDate().toString(), storedDateEndTime.toLocalTime().toString());

            //Create Object
            Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, location, apptType,
                    formattedStart, formattedEnd, userID, customerNameCombo.getValue().getCustomerID(), contact);
            //Save Local
            saveAppointment.add(appointment);

            // Confirm Saving Object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Save New Appointment for " + customerName + "?");
            alert.setContentText("Press OK to save Appointment. Press Cancel to continue editing Appointment.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                AppointmentDAO.insertAppointment(saveAppointment.get(0));
                ObservableList<Appointment> newAppt = AppointmentDAO.getAppointment(customerNameCombo.getValue().getCustomerID());
                customerNameCombo.getValue().addAssociatedAppointment(newAppt.get(newAppt.size()-1)); //size of 1
                System.out.println(customerNameCombo.getValue().getAllAssociatedAppointment().toString());
                AppointmentDAO.displayAllAppointments();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                stage.close();

            } else {
                //cancel was selected and object removed from temp list. Continue Editing.
                saveAppointment.remove(0);
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
     * Lamda using CalcInterface timeCalc() method
     * for calculating time in minutes
     * @return a list containing all local times
     */
    public ObservableList<LocalTime> listAppointmentTimes() {
        int startHour = 0;
        int endHour = 24;

        CalcInterface hoursToMins = (n1, n2) -> n1 * n2;
        CalcInterface minsToHours = (n1, n2) -> n1 / n2;
        CalcInterface remainingMins = (n1, n2) -> n1 % n2;

        for (int i = hoursToMins.timeCalc(startHour,60); i < hoursToMins.timeCalc(endHour,60); i += 15) {

            int hour = minsToHours.timeCalc(i, 60);
            int min = remainingMins.timeCalc(i, 60);
            LocalTime timeSlotTime = LocalTime.of(hour, min);
            appointments.add(timeSlotTime);
        }
        return appointments;
    }
}

