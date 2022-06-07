package controller;

import dao.AppointmentDAO;
import dao.CountryDAO;
import dao.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Report1 implements Initializable {

    public ComboBox month;
    public ComboBox apptType;
    public Button submitButton;
    public TextField resultCount;

    /**
     * Initialize scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try
        {
            ObservableList<String> apptTypes = FXCollections.observableArrayList();
            apptTypes.add(0,"Planning Session");
            apptTypes.add(1,"De-Briefing");
            apptTypes.add(2,"Coffee ");
            apptTypes.add(3,"Business Meeting");

            apptType.setItems(apptTypes);

            ObservableList<String> months = FXCollections.observableArrayList();
            months.add(0,"January");
            months.add(1,"February");
            months.add(2,"March ");
            months.add(3,"April");
            months.add(4,"May");
            months.add(5,"June");
            months.add(6,"July");
            months.add(7,"August");
            months.add(8,"September");
            months.add(9,"October");
            months.add(10,"November");
            months.add(11,"December");

            month.setItems(months);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent actionEvent) throws SQLException {
        try {
            int monthSelect = month.getSelectionModel().getSelectedIndex() + 1;
            String apptTypeSelect = apptType.getValue().toString();

            int result = AppointmentDAO.getAppointmentCount((monthSelect), apptTypeSelect);
            resultCount.setText("" + result + "");
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Code 400: Bad Request.");
            alert.setHeaderText("Exception has occurred.");
            alert.setContentText("Month and Type selections were not selected. Please select an option for both Month and Type.");

            alert.showAndWait();
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }


    }
}
