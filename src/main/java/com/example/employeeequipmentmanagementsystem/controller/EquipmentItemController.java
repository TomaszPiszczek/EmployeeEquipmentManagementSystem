package com.example.employeeequipmentmanagementsystem.controller;

import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemController implements Initializable {

    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label price;
    public void setData(Equipment equipment){
       description.setText(equipment.getDescription());
       if(equipment.getImageData() != null){
           Image img = new Image(new ByteArrayInputStream(equipment.getImageData()));
           image.setImage(img);
       }
        name.setText(equipment.getName());
        price.setText(equipment.getPrice().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
