package com.denzhn.employeesystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane splashParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SplashScreen().start();
    }

    private class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Platform.runLater(() -> {
                    try {
                        splashParent.getScene().getWindow().hide();
                        FXMLLoader fxmlLoader = new FXMLLoader(ESApplication.class.getResource("es-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 621, 554);
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image(ESApplication.class.getResourceAsStream("hr.png")));
                        stage.setResizable(false);
                        stage.setTitle("Employee Management System - v1.0.0 ALPHA");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.exit(-1);
                    }
                });
            } catch (InterruptedException e) {
                System.exit(-1);
            }
        }
    }
}
