package com.example.employeeequipmentmanagementsystem.controller;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StageSettings {
    private double x =0;
    private double y =0;
    public void setStage(Stage stage, Parent root) {
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
    }
}
