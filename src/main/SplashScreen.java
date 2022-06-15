package main;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    public SplashScreen() {

    }

    @Override
    public void init() throws Exception {

        Parent splashRoot = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));
        scene = new Scene(splashRoot);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.preloaderStage = stage;

        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification notification) {
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification notification) {
        if (notification.getType() == StateChangeNotification.Type.BEFORE_START) {

            preloaderStage.hide();

        }
    }
}


