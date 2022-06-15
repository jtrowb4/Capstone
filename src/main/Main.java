package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * class Main.java
 * Main method to start
 * application v1.0.0.
 * @author James Trowbridge
 */
public class Main extends Application {
    /**
     * Utilize the start method and set the stage
     */
    private static final int LOADER_LIMIT = 3;

    @Override
    public void start(Stage stage) throws Exception {

        Locale currentLocale = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("utility.LanguageBundle", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/view/LoginScreen.fxml"), resource);
        Scene scene = new Scene(root);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void init() throws Exception{

        for (int i = 1; i <= LOADER_LIMIT; i++) {
            double progress =(double) i/10;
            System.out.println("progress: " +  progress);
            Thread.sleep(3000);
        }

    }
    
    /**
     * @param args Initial Launch of application - aPEELing
     */
        
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
        launch(args);
    }
}
