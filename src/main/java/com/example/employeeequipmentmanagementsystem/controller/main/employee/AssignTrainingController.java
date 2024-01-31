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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class AssignTrainingController implements Initializable {

    private static AssignTrainingController instance;
    private List<UUID> trainingsUUIDList = new ArrayList<>();
    private UUID employeeUUID;





    private AssignTrainingController() {
    }

    public static AssignTrainingController getInstance() {
        if (instance == null) {
            instance = new AssignTrainingController();
        }
        return instance;
    }
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
        updateTrainings();
    }

    @FXML
    void assignTraining(MouseEvent event) {
        LocalDate selectedDate = date.getValue();

        if (selectedDate == null) {
            showAlert("Nie wybrano daty", "Proszę wybrać datę szkolenia.");
            return;
        }

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, midnight);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = selectedDateTime.format(formatter);

        for (UUID uuid : trainingsUUIDList) {
            TrainingService.assignTrainingToEmployee(employeeUUID, uuid, formattedDateTime);
        }

        dashboardController.switchToEmployeeDetailStage(dashboardController.getEmployeeUUID());
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void createTraining(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/employee/training/create_training_form.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Utwórz badanie");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));

        CreateTrainingController createTrainingController = fxmlLoader.getController();
        createTrainingController.setStage((Stage) root.getScene().getWindow());

        createTrainingController.setAssignTrainingController(this);

        stage.showAndWait();
    }

    public void setStage(Stage window) {
    this.stage = window;
    }
    public void updateTrainings(){
        clearChildren(trainingLayout);
        List<Training> trainings ;
        trainings = TrainingService.getTrainings();
        dashboardController.printDataInColumns(trainings,"training_item_noDate.fxml", TrainingItemControllerNoDate.class,trainingLayout);
    }

    private void clearChildren(VBox trainingLayout) {
        if (trainingLayout.getChildren().size() > 1) {
            trainingLayout.getChildren().subList(1, trainingLayout.getChildren().size()).clear();
        }
    }


    public void addToList(UUID trainingId) {
        trainingsUUIDList.add(trainingId);
    }

    public void removeFromList(UUID trainingId) {
        trainingsUUIDList.remove(trainingId);
    }

    public void setEmployeeUUID(UUID employeeUUID) {
        this.employeeUUID = employeeUUID;
    }
}