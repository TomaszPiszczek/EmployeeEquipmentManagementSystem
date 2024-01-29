package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.item.DataItemController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemDetailsController implements Initializable, DataItemController {

    @FXML
    private Label assignDate;

    @FXML
    private Label description;

    @FXML
    private Label name;

    @FXML
    private Label number;

    public void setData(Object data) {
        if (data instanceof Equipment equipment) {
            description.setText(equipment.getDescription());
            name.setText(equipment.getName());

            String formattedAssignDate = formatAssignDate(equipment.getAssignDate());
            assignDate.setText(formattedAssignDate);
        }
    }

    private String formatAssignDate(String assignDate) {
        if (assignDate != null && assignDate.contains("T")) {
            return assignDate.split("T")[0];
        } else {
            return assignDate;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void delete(ActionEvent event) {

    }
}

