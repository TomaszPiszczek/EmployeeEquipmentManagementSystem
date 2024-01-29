package com.example.employeeequipmentmanagementsystem.controller.main.employee;

import com.example.employeeequipmentmanagementsystem.controller.item.TrainingItemController;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssignTrainingController implements Initializable {
    @FXML
    private DatePicker date;

    @FXML
    private HBox employeeColumn1;

    @FXML
    private VBox trainingLayout;

    DashboardController dashboardController;

    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardController = DashboardController.getInstance();


        List<Training> trainings ;
        trainings = TrainingService.getTrainings();
        dashboardController.printDataInColumns(trainings,"training_item.fxml", TrainingItemController.class,trainingLayout);
    }

    @FXML
    void assignTraining(MouseEvent event) {

    }

    @FXML
    void createTraining(MouseEvent event) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/employee/create_training_form.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Utwórz badanie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            CreateTrainingController createTrainingController = fxmlLoader.getController();
            createTrainingController.setStage((Stage) root.getScene().getWindow());

            stage.showAndWait();

    }


    public void setStage(Stage window) {
    this.stage = window;
    }
}