package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TrainingItemController implements Initializable, DataItemController {
    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label date;
    @FXML
    private HBox column;
    private Training training;
    private DashboardController dashboardController;
    public void setData(Object data){
        if (data instanceof Training training) {
            this.training = (Training) data;
            description.setText(training.getDescription());
            name.setText(training.getName());
            date.setText(formatAssignDate(training.getExpireDate()));
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usunięcia");
        alert.setHeaderText("Czy na pewno chcesz usunąć to badanie");
        alert.setContentText("Tej operacji nie można cofnąć.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TrainingService.removeTrainingFromEmployee(training.getTrainingId());
            dashboardController.switchToEmployeeDetailStage(dashboardController.getEmployeeUUID());
        } else {
            alert.close();
        }
    }
}
