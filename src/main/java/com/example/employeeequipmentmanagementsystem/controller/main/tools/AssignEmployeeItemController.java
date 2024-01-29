package com.example.employeeequipmentmanagementsystem.controller.main.tools;

import com.example.employeeequipmentmanagementsystem.controller.item.DataItemController;
import com.example.employeeequipmentmanagementsystem.controller.login.LoginController;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignEmployeeItemController implements Initializable, DataItemController {

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private HBox column;

    private Employee employee;
    private DashboardController dashboardController;
    private AssignToolController assignToolController;
    private boolean isSelected = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assignToolController = AssignToolController.getInstance();
        this.dashboardController = DashboardController.getInstance();
        column.setOnMouseClicked(event -> handleItemClick());
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Employee) {
            employee = (Employee) data;
            name.setText(employee.getName());
            surname.setText(employee.getSurname());
        }
    }





    private void handleItemClick() {
        isSelected = !isSelected;
        updateItemStyle();

            if (isSelected) {
                dashboardController.addToEmployeeUUIDList(employee.getEmployeeId());
            } else {
                dashboardController.removeFromEmployeeUUIDList(employee.getEmployeeId());
            }

    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
        }
    }
}
