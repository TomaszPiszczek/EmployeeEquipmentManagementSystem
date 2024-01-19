package com.example.employeeequipmentmanagementsystem;

import com.example.employeeequipmentmanagementsystem.controller.StageSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        Parent root =  FXMLLoader.load(getClass().getResource("main/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login");

        StageSettings.setStage(stage,root);

        root.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        stage.initStyle(StageStyle.TRANSPARENT);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}