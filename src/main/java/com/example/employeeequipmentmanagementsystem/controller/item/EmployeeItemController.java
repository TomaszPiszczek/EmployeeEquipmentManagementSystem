package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EmployeeItemController implements Initializable, DataItemController {

    @FXML
    private Label numberOfTools;

    @FXML
    private Label daysToTraining;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private HBox column;

    private DashboardController dashboardController;

    private Boolean isSelected = false;

    private Employee employee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.dashboardController = DashboardController.getInstance();



        // Now you can safely use the dashboardController
            column.setOnMouseClicked(event -> handleItemClick());

            // Handle the case where the dashboardController is not set yet

    }

    private void handleItemClick() {
        isSelected = !isSelected;
        updateItemStyle();
    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            dashboardController.addToEmployeeUUIDList(employee.getEmployeeId());

        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
            dashboardController.removeFromEmployeeUUIDList(employee.getEmployeeId());
        }
    }

    private UUID getEmployeeUUID() {
        return employee.getEmployeeId();
    }

    public void setData(Object data) {
        if (data instanceof Employee) {
            employee = (Employee) data;
            updateItemStyle();
            numberOfTools.setText(employee.getNumberOfTools().toString());
            daysToTraining.setText(employee.getDaysToTraining().toString());
            name.setText(employee.getName());
            surname.setText(employee.getSurname());
        }
    }
    @FXML
    private void switchForm(ActionEvent event) {

        if (dashboardController != null) {
            dashboardController.switchForms(event,getEmployeeUUID());
            System.out.println(getEmployeeUUID());
        }

    }


}
