package com.example.employeeequipmentmanagementsystem.controller.controller;

import com.example.employeeequipmentmanagementsystem.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainingItemController implements Initializable,DataItemController{
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label price;
    public void setData(Object data){
        if (data instanceof Training training) {
            description.setText(training.getDescription());
            name.setText(training.getName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
