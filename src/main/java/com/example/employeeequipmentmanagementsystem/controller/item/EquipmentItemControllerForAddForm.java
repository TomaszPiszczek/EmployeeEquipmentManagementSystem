package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.main.employee.AddToolFormController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.example.employeeequipmentmanagementsystem.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemControllerForAddForm implements Initializable, DataItemController {

    @FXML
    private HBox column;

    @FXML
    private Label description;

    @FXML
    private Label name;

    private Boolean isSelected = false;
    private Equipment equipment;
    private AddToolFormController addToolFormController;

    @Override
    public void setData(Object data) {
        if (data instanceof Equipment equipment) {
            this.equipment = (Equipment) data;
            description.setText(equipment.getDescription());
            name.setText(equipment.getName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column.setOnMouseClicked(this::handleItemClick);
        addToolFormController = AddToolFormController.getInstance();
    }

    private void handleItemClick(MouseEvent event) {
        isSelected = !isSelected;
        updateItemStyle();
    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            addToolFormController.addToolToList(equipment.getEquipmentId());

        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
            addToolFormController.removeToolFromList(equipment.getEquipmentId());

        }
    }
}
