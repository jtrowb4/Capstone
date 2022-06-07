package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * class Main.java
 * Main method to start application
 * application v1.2.0.
 * @author James Trowbridge
 */
public class Main extends Application {
    /**
     * Utilize the start method and set the stage
     */
    @Override
    public void start(Stage stage) throws Exception {

        Locale currentLocale = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("utility.LanguageBundle", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/view/LoginScreen.fxml"), resource);
        stage.setTitle("QAM2_JavaApplication");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * @param args Initial Launch of application - QAM2_JavaApplication
     */
    public static void main(String[] args) {
        launch(args);
    }
}
