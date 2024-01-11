package com.example.employeeequipmentmanagementsystem.controller;

import com.example.employeeequipmentmanagementsystem.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeItemController implements Initializable {

    @FXML
    private Label numberOfTools;

    @FXML
    private Label daysToTraining;

    @FXML
    private Label name;

    @FXML
    private Label surname;
    public void setData(Employee employee){
        numberOfTools.setText(employee.getNumberOfTools().toString());
        daysToTraining.setText(employee.getDaysToTraining().toString());
        name.setText(employee.getName());
        surname.setText(employee.getSurname());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
