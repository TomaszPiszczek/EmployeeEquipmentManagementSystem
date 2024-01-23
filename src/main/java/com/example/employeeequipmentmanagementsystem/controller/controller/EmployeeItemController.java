package com.example.employeeequipmentmanagementsystem.controller.controller;

import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EmployeeItemController implements Initializable,DataItemController {

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
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

    private BooleanProperty clicked = new SimpleBooleanProperty(false);


    private Employee employee; // Track the current employee

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            clicked.set(!clicked.get());
            updateStyles();

        });
    }

    private UUID getEmployeeUUID() {
        return employee.getEmployeeId();
    }

    public void setData(Object data) {
        if (data instanceof Employee) {
            employee = (Employee) data;
            numberOfTools.setText(employee.getNumberOfTools().toString());
            daysToTraining.setText(employee.getDaysToTraining().toString());
            name.setText(employee.getName());
            surname.setText(employee.getSurname());
        }
    }

    private void updateStyles() {
        if (clicked.get()) {
            column.setStyle("-fx-background-color: dodgerblue;");
        } else {
            column.setStyle("-fx-background-color: lightgray;");
        }
    }
    @FXML
    private void switchForm(ActionEvent event) {

        if (dashboardController != null) {
            dashboardController.switchForms(event,getEmployeeUUID());
        }

    }


}
