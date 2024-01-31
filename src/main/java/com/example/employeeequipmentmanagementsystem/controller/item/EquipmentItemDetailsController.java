package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.item.DataItemController;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EquipmentItemDetailsController implements Initializable, DataItemController {

    @FXML
    private Label assignDate;

    @FXML
    private Label description;

    @FXML
    private Label name;

    @FXML
    private Label number;
    private Equipment equipment;
    private DashboardController dashboardController;

    public void setData(Object data) {
        if (data instanceof Equipment equipment) {
            this.equipment = equipment;
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
        this.dashboardController = DashboardController.getInstance();
    }

    @FXML
    void delete(ActionEvent event) {
        EquipmentService.removeEquipmentFromEmployee(equipment.getEquipmentId());
        dashboardController.switchToEmployeeDetailStage(dashboardController.getEmployeeUUID());
    }
}

