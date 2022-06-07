package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.*;


/**
 * class AppointmentDAO.java
 *
 * @author James Trowbridge
 *
 */
public class AppointmentDAO {


    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static String joinCall = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, client_schedule.appointments.Contact_ID, Contact_Name " +
            "FROM client_schedule.appointments, client_schedule.contacts " +
            "WHERE client_schedule.appointments.Contact_ID = client_schedule.contacts.Contact_ID";

    private static String weekApptsQuery = joinCall + " AND week(start)= week(now())";
    private static String monthApptsQuery = joinCall + " AND Month(start)= Month(now())";

    private static ZoneId userZoneID = ZoneId.systemDefault();
    /**
     * displayAllAppointments gets all appointments for tableview
     * @return allAppointments
     */
    public static ObservableList<Appointment> displayAllAppointments() throws SQLException, Exception {

        if (allAppointments.size() > 0){
            allAppointments.clear();
        }
        DBConnector.openConnection();
        DBQuery.makeQuery(joinCall + " ORDER BY client_schedule.appointments.Appointment_ID");
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String appointmentTitle = result.getString("Title");
            String appointmentDescription = result.getString("Description");
            String appointmentLocation = result.getString("Location");
            String appointmentType = result.getString("Type");

            String dateTime = result.getString("Start");
            String[] utcSplit = dateTime.split(" ");
            LocalDate utcDate = LocalDate.parse(utcSplit[0]);
            LocalTime utcTime = LocalTime.parse(utcSplit[1]);
            LocalDateTime utcDateTime = LocalDateTime.of(utcDate,utcTime);
            ZoneId zoneIdLocal = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String startDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String startTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentStart = startDate + " " + startTime;

            dateTime = result.getString("End");
            utcSplit = dateTime.split(" ");
            utcDate = LocalDate.parse(utcSplit[0]);
            utcTime = LocalTime.parse(utcSplit[1]);
            utcDateTime = LocalDateTime.of(utcDate,utcTime);
            zoneIdLocal = ZoneId.systemDefault();
            utcZone = ZoneId.of("UTC");
            utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String endDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String endTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentEnd = endDate + " " + endTime;

            int userID = result.getInt("User_ID");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            addAppointment(new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStart, appointmentEnd, userID, customerID, contactID, contactName));
        }
        DBConnector.closeConnection();
        return allAppointments;
    }
    /**
     * addAppointments adds appointments to all appointment list
     */
    public static void addAppointment(Appointment appt) {
        allAppointments.add(appt);

    }
    /**
     * insertAppointment used to add appointment to DB
     */
    public static void insertAppointment(Appointment appointment) {
        DBConnector.openConnection();
        String insertCustomerQuery = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, " +
                "Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES('"
                + appointment.getAppointmentID() + "', '"
                + appointment.getTitle() + "', '"
                + appointment.getDescription() + "', '"
                + appointment.getLocation() + "', '"
                + appointment.getType() + "', '"
                + appointment.getStartTime() + "', '"
                + appointment.getEndTime() + "', '"
                + appointment.getCustomerID() + "', '"
                + appointment.getUserID() + "', '"
                + appointment.getContactID() + "')";

        DBQuery.makeQuery(insertCustomerQuery);
        DBConnector.closeConnection();
    }
    /**
     * updateAppointment used to update appointment record on DB
     */
    public static void updateAppointment(Appointment appointment) {
        DBConnector.openConnection();
        String updateCustomerQuery =
                "UPDATE client_schedule.appointments " +
                        "SET Title = '" + appointment.getTitle() +
                        "', Description = '" + appointment.getDescription() +
                        "', Location = '" + appointment.getLocation() +
                        "', Type = '" + appointment.getType() +
                        "', Start = '" + appointment.getStartTime() +
                        "', End = '" + appointment.getEndTime() +
                        "', Customer_ID = '" + appointment.getCustomerID() +
                        "', User_ID = '" + appointment.getUserID() +
                        "', Contact_ID = '" + appointment.getContactID() +

                        "' WHERE Appointment_ID = '" + appointment.getAppointmentID() + "'";
        DBQuery.makeQuery(updateCustomerQuery);
        DBConnector.closeConnection();
    }
    /**
     * deleteAppointment used to delete appointment record from DB
     */
    public static void deleteAppointment(Appointment appointment){
        DBConnector.openConnection();
        String deleteCustomerQuery = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = '" + appointment.getAppointmentID() + "'";
        DBQuery.makeQuery(deleteCustomerQuery);
        DBConnector.closeConnection();
    }

    public static ObservableList<Appointment> displayThisWeekAppointments() throws SQLException, Exception {

        if (allAppointments.size() > 0){
            allAppointments.clear();
        }
        DBConnector.openConnection();
        DBQuery.makeQuery(weekApptsQuery + " ORDER BY client_schedule.appointments.Appointment_ID");
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String appointmentTitle = result.getString("Title");
            String appointmentDescription = result.getString("Description");
            String appointmentLocation = result.getString("Location");
            String appointmentType = result.getString("Type");

            String dateTime = result.getString("Start");
            String[] utcSplit = dateTime.split(" ");
            LocalDate utcDate = LocalDate.parse(utcSplit[0]);
            LocalTime utcTime = LocalTime.parse(utcSplit[1]);
            LocalDateTime utcDateTime = LocalDateTime.of(utcDate,utcTime);
            ZoneId zoneIdLocal = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String startDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String startTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentStart = startDate + " " + startTime;

            dateTime = result.getString("End");
            utcSplit = dateTime.split(" ");
            utcDate = LocalDate.parse(utcSplit[0]);
            utcTime = LocalTime.parse(utcSplit[1]);
            utcDateTime = LocalDateTime.of(utcDate,utcTime);
            zoneIdLocal = ZoneId.systemDefault();
            utcZone = ZoneId.of("UTC");
            utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String endDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String endTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentEnd = endDate + " " + endTime;

            int userID = result.getInt("User_ID");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            addAppointment(new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStart, appointmentEnd, userID, customerID, contactID));
        }
        DBConnector.closeConnection();
        return allAppointments;
    }

    public static ObservableList<Appointment> displayThisMonthAppointments() throws SQLException, Exception {

        if (allAppointments.size() > 0){
            allAppointments.clear();
        }
        DBConnector.openConnection();
        DBQuery.makeQuery(monthApptsQuery + " ORDER BY client_schedule.appointments.Appointment_ID");
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String appointmentTitle = result.getString("Title");
            String appointmentDescription = result.getString("Description");
            String appointmentLocation = result.getString("Location");
            String appointmentType = result.getString("Type");

            String dateTime = result.getString("Start");
            String[] utcSplit = dateTime.split(" ");
            LocalDate utcDate = LocalDate.parse(utcSplit[0]);
            LocalTime utcTime = LocalTime.parse(utcSplit[1]);
            LocalDateTime utcDateTime = LocalDateTime.of(utcDate,utcTime);
            ZoneId zoneIdLocal = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String startDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String startTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentStart = startDate + " " + startTime;

            dateTime = result.getString("End");
            utcSplit = dateTime.split(" ");
            utcDate = LocalDate.parse(utcSplit[0]);
            utcTime = LocalTime.parse(utcSplit[1]);
            utcDateTime = LocalDateTime.of(utcDate,utcTime);
            zoneIdLocal = ZoneId.systemDefault();
            utcZone = ZoneId.of("UTC");
            utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String endDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String endTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentEnd = endDate + " " + endTime;

            int userID = result.getInt("User_ID");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            addAppointment(new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStart, appointmentEnd, userID, customerID, contactID));
        }
        DBConnector.closeConnection();
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointment(int customer_ID) throws SQLException {

        if (allAppointments.size() > 0){
            allAppointments.clear();
        }

        DBConnector.openConnection();
        DBQuery.makeQuery("SELECT * FROM client_schedule.appointments WHERE Customer_ID = '" + customer_ID + "'");
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String appointmentTitle = result.getString("Title");
            String appointmentDescription = result.getString("Description");
            String appointmentLocation = result.getString("Location");
            String appointmentType = result.getString("Type");

            String dateTime = result.getString("Start");
            String[] utcSplit = dateTime.split(" ");
            LocalDate utcDate = LocalDate.parse(utcSplit[0]);
            LocalTime utcTime = LocalTime.parse(utcSplit[1]);
            LocalDateTime utcDateTime = LocalDateTime.of(utcDate,utcTime);
            ZoneId zoneIdLocal = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String startDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String startTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentStart = startDate + " " + startTime;

            dateTime = result.getString("End");
            utcSplit = dateTime.split(" ");
            utcDate = LocalDate.parse(utcSplit[0]);
            utcTime = LocalTime.parse(utcSplit[1]);
            utcDateTime = LocalDateTime.of(utcDate,utcTime);
            zoneIdLocal = ZoneId.systemDefault();
            utcZone = ZoneId.of("UTC");
            utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String endDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String endTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentEnd = endDate + " " + endTime;

            int userID = result.getInt("User_ID");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            addAppointment(new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStart, appointmentEnd, userID, customerID, contactID));
        }
        DBConnector.closeConnection();
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointment(String contact_Name) throws SQLException {

        if (allAppointments.size() > 0){
            allAppointments.clear();
        }

        DBConnector.openConnection();
        String query = joinCall + " AND client_schedule.contacts.Contact_Name = '" + contact_Name + "'";
        DBQuery.makeQuery(query);
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String appointmentTitle = result.getString("Title");
            String appointmentDescription = result.getString("Description");
            String appointmentLocation = result.getString("Location");
            String appointmentType = result.getString("Type");

            String dateTime = result.getString("Start");
            String[] utcSplit = dateTime.split(" ");
            LocalDate utcDate = LocalDate.parse(utcSplit[0]);
            LocalTime utcTime = LocalTime.parse(utcSplit[1]);
            LocalDateTime utcDateTime = LocalDateTime.of(utcDate,utcTime);
            ZoneId zoneIdLocal = ZoneId.systemDefault();
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String startDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String startTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentStart = startDate + " " + startTime;

            dateTime = result.getString("End");
            utcSplit = dateTime.split(" ");
            utcDate = LocalDate.parse(utcSplit[0]);
            utcTime = LocalTime.parse(utcSplit[1]);
            utcDateTime = LocalDateTime.of(utcDate,utcTime);
            zoneIdLocal = ZoneId.systemDefault();
            utcZone = ZoneId.of("UTC");
            utcZDT = ZonedDateTime.of(utcDateTime, utcZone);
            localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), zoneIdLocal);
            String endDate = localZDT.toLocalDateTime().toLocalDate().toString();
            String endTime = localZDT.toLocalDateTime().toLocalTime().toString();
            String appointmentEnd = endDate + " " + endTime;

            int userID = result.getInt("User_ID");
            int customerID = result.getInt("Customer_ID");
            int contactID = result.getInt("Contact_ID");
            addAppointment(new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation,
                    appointmentType, appointmentStart, appointmentEnd, userID, customerID, contactID));
        }
        DBConnector.closeConnection();
        return allAppointments;
    }

    public static int getAppointmentCount(int month, String type) throws SQLException {

        int resultCount = 0;

        DBConnector.openConnection();
        String query = "SELECT COUNT(*) AS Appt_Count FROM client_schedule.appointments WHERE Type = '" + type + "'" + " AND Month(Start) = '" + month + "'";
        DBQuery.makeQuery(query);
        ResultSet result = DBQuery.getResult();
        while (result.next()) {
            resultCount = result.getInt("Appt_Count");

        }
        DBConnector.closeConnection();
        return resultCount;
    }
}



