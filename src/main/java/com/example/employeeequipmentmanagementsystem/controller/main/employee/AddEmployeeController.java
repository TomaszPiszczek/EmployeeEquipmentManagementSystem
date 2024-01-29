package com.example.employeeequipmentmanagementsystem.controller.main.employee;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.net.http.HttpRequest;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    void close(ActionEvent event) {

    }
    Stage stage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void closeForm() {
        if (stage != null) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
     void addEmployee(ActionEvent event) {
        EquipmentApiConnection.callApi("employee/addEmployee?name="+name.getText() +"&surname="+surname.getText(),"POST", HttpRequest.BodyPublishers.noBody(),String.class);


        closeForm();
    }


}
