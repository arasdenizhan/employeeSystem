package com.denzhn.employeesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ESApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ESApplication.class.getResource("es-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 621, 554);
        stage.getIcons().add(new Image(ESApplication.class.getResourceAsStream("hr.png")));
        stage.setResizable(false);
        stage.setTitle("Employee Management System - v1.0.0 ALPHA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}