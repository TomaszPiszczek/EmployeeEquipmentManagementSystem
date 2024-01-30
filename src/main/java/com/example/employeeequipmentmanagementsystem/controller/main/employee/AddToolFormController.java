package com.example.employeeequipmentmanagementsystem.controller.main.employee;


import com.example.employeeequipmentmanagementsystem.controller.item.EquipmentItemControllerForAddForm;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddToolFormController implements Initializable {
    DashboardController dashboardController;
    private Stage stage;

    private static AddToolFormController instance;

    @FXML
    private HBox employeeColumn1;

    @FXML
    private VBox toolLayout;
    @FXML
    private DatePicker date;
    Set<UUID> toolUUIDList = new HashSet<>();
    UUID employeeUUID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardController = DashboardController.getInstance();
        updateTools();
    }

    private AddToolFormController() {
    }

    public static AddToolFormController getInstance() {
        if (instance == null) {
            instance = new AddToolFormController();
        }
        return instance;
    }

    @FXML
    void assignTools(MouseEvent event) {
        LocalDate selectedDate = date.getValue();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, midnight);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = selectedDateTime.format(formatter);



        EquipmentService.assignEquipmentsToEmployees(Set.of(employeeUUID),toolUUIDList,formattedDateTime);
    }
    public void setStage(Stage window) {
        this.stage = window;
    }

    public void updateTools(){
        clearChildren(toolLayout);
        List<Equipment> equipment ;
        equipment = EquipmentService.getEquipment();

        dashboardController.printDataInColumns(equipment,"equipment_item_for_add_tool_to_employee.fxml", EquipmentItemControllerForAddForm.class,toolLayout);
    }

    private void clearChildren(VBox trainingLayout) {
        if (trainingLayout.getChildren().size() > 1) {
            trainingLayout.getChildren().subList(1, trainingLayout.getChildren().size()).clear();
        }
    }
    public void addToolToList(UUID toolUUID){
        toolUUIDList.add(toolUUID);
    }
    public void removeToolFromList(UUID toolUUID){
        toolUUIDList.remove(toolUUID);
    }

    public void setEmployeeUUID(UUID employeeUUID) {
        this.employeeUUID = employeeUUID;
    }
}
