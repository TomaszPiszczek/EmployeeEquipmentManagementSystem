package com.example.employeeequipmentmanagementsystem.controller.controller;

import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemController implements Initializable,DataItemController {

    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label price;
    public void setData(Object data){
        if (data instanceof Equipment equipment) {
            description.setText(equipment.getDescription());
            if (equipment.getImageData() != null) {
                String imageDataString = equipment.getImageData();
                byte[] imageDataBytes = imageDataString.getBytes();

                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageDataBytes);
                Image img = new Image(inputStream);
                image.setImage(img);
            }
            name.setText(equipment.getName());
            if (equipment.getPrice() !=null){
                price.setText(equipment.getPrice().toString());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
