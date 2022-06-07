package controller;

import dao.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * class MainMenu.java
 *
 * @author James Trowbridge
 *
 */
public class MainMenu implements Initializable {
    public Button logoutButton;
    public Button scheduleButton;
    public Button customerButton;
    public Button reportButton;


    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment>appointments = null;
        try {
            appointments = AppointmentDAO.displayAllAppointments();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int apptCounter = 0;
        for (Appointment appointment: appointments)
        {
            String apptTimeString = appointment.getStartTime();
            String[] daySplit = apptTimeString.split(" ");
            LocalDate apptDate = LocalDate.parse(daySplit[0]);
            LocalTime apptTime = LocalTime.parse(daySplit[1]);
            LocalDateTime apptLDT = LocalDateTime.of(apptDate, apptTime); //appt in UTC
            //ZoneId zoneId = ZoneId.of("UTC");
            LocalDateTime todayNow = LocalDateTime.now();
            LocalDate today = todayNow.toLocalDate();
            LocalTime timeNow = todayNow.toLocalTime();
            LocalTime futureTime = timeNow.plusMinutes(15);
            int hour = futureTime.getHour();
            int min = futureTime.getMinute();

            if (apptDate.equals(today))
            {
                int apptTimeMins = (apptLDT.getHour()*60) + (apptLDT.getMinute());
                int nowMins = (todayNow.getHour()*60) + (todayNow.getMinute());
                int futureMins = (hour * 60) + min;

                if(apptTimeMins >= nowMins && apptTimeMins <=futureMins){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Please Advise");
                    alert.setHeaderText("Upcoming Appointment.");
                    alert.setContentText("Appointment: (" + appointment.getAppointmentID() + ") - '" + appointment.getTitle() + "' is happening with the next 15 minutes.");
                    alert.show();
                    break;
                }
                else if(++apptCounter == appointments.size())
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Please Advise");
                    alert.setHeaderText("No Upcoming Appointment.");
                    alert.setContentText("There are currently no upcoming appointments happening within the next 15 minutes.");
                    alert.show();
                    break;
                }
            }
            else if(++apptCounter == appointments.size()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Please Advise");
                alert.setHeaderText("No Upcoming Appointment.");
                alert.setContentText("There are currently no upcoming appointments happening within the next 15 minutes.");
                alert.show();
            }
        }

    }

    /**
     * Back button that loads LoginMenu
     */
    public void toLoginScreen(ActionEvent actionEvent) throws IOException {
    Locale currentLocale = Locale.getDefault();
    ResourceBundle resource = ResourceBundle.getBundle("utility.LanguageBundle", currentLocale);
    FXMLLoader fxmlLoader = new FXMLLoader();
    Parent root = fxmlLoader.load(getClass().getResource("/view/LoginScreen.fxml"), resource);
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
     * Loads Customer Menu - allows for add, modify, and deleting of Customers
     */
    public void toCustomerMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("QAM2_JavaApplication: Customer Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *
     * @param actionEvent
     * @throws IOException
     * Loads Appointment Menu - allows for add, modify, and deleting of Appointments
     */
    public void toAppointmentMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("QAM2_JavaApplication: Appointment Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**
     *
     * @param actionEvent
     * @throws IOException
     * Loads Reports Menu
     */
    public void toReportsMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("QAM2_JavaApplication: Reports Menu");
        stage.setScene(scene);
        stage.show();
    }

}
