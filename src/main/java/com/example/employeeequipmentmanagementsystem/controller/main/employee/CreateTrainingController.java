package com.example.employeeequipmentmanagementsystem.controller.main.employee;

import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTrainingController {
    @FXML
    private TextField description;

    @FXML
    private TextField name;
    private AssignTrainingController assignTrainingController;
    Stage stage;
    @FXML
    void addTraining(ActionEvent event) {
        TrainingService.createTraining(name.getText(),description.getText());
        if (assignTrainingController != null) {
            assignTrainingController.updateTrainings();
        }
        closeForm();
    }
    @FXML
    void close(ActionEvent event) {
        closeForm();
    }
    private void closeForm() {
        if (stage != null) {
            stage.close();
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAssignTrainingController(AssignTrainingController assignTrainingController) {
        this.assignTrainingController = assignTrainingController;
    }
}
