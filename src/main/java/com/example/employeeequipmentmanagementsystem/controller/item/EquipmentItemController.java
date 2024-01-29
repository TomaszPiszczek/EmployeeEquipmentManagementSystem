package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemController implements Initializable, DataItemController {

    @FXML
    private Label description;
    @FXML
    private HBox column;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;

    private DashboardController dashboardController;




    private Equipment equipment;
    private boolean isSelected = false;

    public void setData(Object data) {

        if (data instanceof Equipment equipment) {
            this.equipment = equipment;
            updateItemStyle();
            description.setText(equipment.getDescription());

            if (equipment.getImageData() != null) {
                String imageDataString = equipment.getImageData();
                byte[] imageDataBytes = imageDataString.getBytes();

                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageDataBytes);
                Image img = new Image(inputStream);
                image.setImage(img);
            }

            name.setText(equipment.getName());
            if (equipment.getPrice() != null) {
                price.setText(equipment.getPrice().toString());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardController = DashboardController.getInstance();


        column.setOnMouseClicked(event -> handleItemClick());
    }

    private void handleItemClick() {
        isSelected = !isSelected;
        updateItemStyle();
    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            System.out.println(dashboardController.hashCode() + "EQUIPMENTITEMCONT");
            dashboardController.addToEquipmentUUIDList(equipment.getEquipmentId());
        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
            dashboardController.removeFromEquipmentUUIDList(equipment.getEquipmentId());
        }
    }
}
