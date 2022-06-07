package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsMenu {
    public Button backButton;
    public Button report1;
    public Button report2;
    public Button report3;


    public void generateReport1(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Report1.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("QAM2_JavaApplication: Report 1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void generateReport2(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Report2.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("QAM2_JavaApplication: Report 2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void generateReport3(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Report3.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("QAM2_JavaApplication: Report 3");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("QAM2_JavaApplication");
        stage.setScene(scene);
        stage.show();
    }
}
