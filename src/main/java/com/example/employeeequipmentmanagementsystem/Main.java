package com.example.employeeequipmentmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private double x =0;
    private double y =0;
    @Override
    public void start(Stage stage) throws IOException {


        Parent root =  FXMLLoader.load(getClass().getResource("login/login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        root.setOnMousePressed((MouseEvent event)->{
            x= event.getSceneX();
            y= event.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        stage.initStyle(StageStyle.TRANSPARENT);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}