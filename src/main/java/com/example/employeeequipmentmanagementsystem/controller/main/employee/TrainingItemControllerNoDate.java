package com.example.employeeequipmentmanagementsystem.controller.main.employee;

import com.example.employeeequipmentmanagementsystem.controller.item.DataItemController;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class TrainingItemControllerNoDate implements Initializable, DataItemController {
    @FXML
    private Label description;
    @FXML
    private Label name;
    @FXML
    private HBox column;
    private Training training;
    private Boolean isSelected = false;
    private AssignTrainingController assignTrainingController;



    public void setData(Object data){
        if (data instanceof Training training) {
            this.training = (Training) data;
            description.setText(training.getDescription());
            name.setText(training.getName());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assignTrainingController = AssignTrainingController.getInstance();

        column.setOnMouseClicked(event -> handleItemClick());


    }
    private void handleItemClick() {
        isSelected = !isSelected;
        updateItemStyle();
    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            System.out.println( training.getTrainingId() + "training lista");
            assignTrainingController.addToList(training.getTrainingId());

        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
            assignTrainingController.removeFromList(training.getTrainingId());
        }
    }


    @FXML
    void delete(ActionEvent event) {
        TrainingService.removeTraining(training.getTrainingId());
        if (assignTrainingController != null) {
            assignTrainingController.updateTrainings();
            System.out.println("non null");
        }
    }

}
