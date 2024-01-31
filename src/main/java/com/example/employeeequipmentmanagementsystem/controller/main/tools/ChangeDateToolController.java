package com.example.employeeequipmentmanagementsystem.controller.main.tools;

import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class ChangeDateToolController implements Initializable {
    @FXML
    private DatePicker date;

    @FXML
    private Label description;

    @FXML
    private Label name;
    private UUID equipmentUUID;

    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    void change(ActionEvent event) {
        LocalDate selectedDate = date.getValue();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, midnight);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = selectedDateTime.format(formatter);



        EquipmentService.changeDate(formattedDateTime,equipmentUUID);

        stage.close();
    }

    public void setStage(Stage window) {
        this.stage = window;
    }

    public void setEquipmentUUID(UUID equipmentUUID) {
        this.equipmentUUID = equipmentUUID;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
