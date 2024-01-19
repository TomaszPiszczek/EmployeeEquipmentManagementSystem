package com.example.employeeequipmentmanagementsystem.controller;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageSettings {
    private static double x =0;
    private static double y =0;
    public static void setStage(Stage stage, Parent root) {
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8);
        });
        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
    }
}
