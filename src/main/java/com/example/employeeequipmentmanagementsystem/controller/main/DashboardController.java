package com.example.employeeequipmentmanagementsystem.controller.main;

import com.example.employeeequipmentmanagementsystem.controller.StageSettings;
import com.example.employeeequipmentmanagementsystem.controller.controller.DataItemController;
import com.example.employeeequipmentmanagementsystem.controller.controller.EmployeeItemController;
import com.example.employeeequipmentmanagementsystem.controller.controller.EquipmentItemController;
import com.example.employeeequipmentmanagementsystem.controller.controller.TrainingItemController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.EmployeeService;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.UUID;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashboardController implements Initializable {

    @FXML
    private VBox employeeLayout;
    @FXML
    private VBox equipmentLayout;
    @FXML
    private VBox trainingLayoutDetails;
    @FXML
    private VBox equipmentLayoutDetails;
    private AnchorPane main_form;
    @FXML
    private Button close;
    @FXML
    private AnchorPane employee_detail_stage;
    @FXML
    private AnchorPane employees_stage;
    @FXML
    private VBox equipmentLayout1;
    @FXML
    private VBox equipmentLayout11;
    @FXML
    private Button logout;
    @FXML
    private AnchorPane main_add_employee;
    @FXML
    private AnchorPane main_delete_employee;
    @FXML
    private AnchorPane main_edit_employee;
    @FXML
    private TextField main_search;
    @FXML
    private Button minimise;
    @FXML
    private Button scene_cars;
    @FXML
    private Button scene_employee;
    @FXML
    private Button scene_tools;
    @FXML
    private AnchorPane tools_stage;
    @FXML
    private HBox employeeColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEmployeeData();
    }

    private void initializeEmployeeData() {
        clearChildren(employeeLayout);
        employeeLayout.setSpacing(1);

        List<Employee> employeeList = EmployeeService.getEmployeesDTO();
        printDataInColumns(employeeList, "employee_item.fxml", EmployeeItemController.class, employeeLayout);
    }

    private void initializeEquipmentData() {
        clearChildren(equipmentLayout);
        equipmentLayout.setSpacing(1);

        List<Equipment> equipmentList = EquipmentService.getEquipment();
        printDataInColumns(equipmentList, "equipment_item.fxml", EquipmentItemController.class, equipmentLayout);
    }
    private void initializeEquipmentDetails(UUID employeeUUID){
        clearChildren(equipmentLayoutDetails);
        equipmentLayoutDetails.setSpacing(1);

        List<Equipment> equipmentList = EquipmentService.getEquipmentForEmployee(employeeUUID);
        printDataInColumns(equipmentList, "equipment_item.fxml", EquipmentItemController.class, equipmentLayoutDetails);

    }
    private void initializeTrainingDetails(UUID employeeUUID) {
        clearChildren(trainingLayoutDetails);
        trainingLayoutDetails.setSpacing(1);

        List<Training> trainingList = TrainingService.getTrainingForEmployee(employeeUUID);
        printDataInColumns(trainingList, "training_item.fxml", TrainingItemController.class, trainingLayoutDetails);
    }

    private void clearChildren(VBox layout) {
        if (layout.getChildren().size() > 1) {
            layout.getChildren().subList(1, layout.getChildren().size()).clear();
        }
    }

    private void switchToEmployeesStage() {
        tools_stage.setVisible(false);
        employee_detail_stage.setVisible(false);
        employees_stage.setVisible(true);
        initializeEmployeeData();
    }

    private void switchToToolsStage() {
        employees_stage.setVisible(false);
        employee_detail_stage.setVisible(false);
        tools_stage.setVisible(true);

        initializeEquipmentData();
    }

    private void switchToCarsStage() {
        // TODO: Implement cars stage logic
    }

    private void switchToEmployeeDetailStage() {
        employees_stage.setVisible(false);
        employee_detail_stage.setVisible(true);
        initializeEquipmentData();
        //todo implement data
    }

    @FXML
    private void switchForm(ActionEvent event) {
       switchForms(event,null);
    }
    public void switchForms(ActionEvent event,UUID uuid){
        if (event.getSource() == scene_employee) {
            switchToEmployeesStage();
        } else if (event.getSource() == scene_tools) {
            switchToToolsStage();
        } else if (event.getSource() == scene_cars) {
            switchToCarsStage();
        } else if (event.getSource() instanceof Node) {
            Node sourceNode = (Node) event.getSource();
            if ("employee_more".equals(sourceNode.getId())) {
                switchToEmployeeDetailStage();
                initializeTrainingDetails(uuid);
                initializeEquipmentDetails(uuid);
            }
        }
    }



    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void minimise() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logout() throws IOException, BackingStoreException {
        logout.getScene().getWindow().hide();
        Preferences.userRoot().clear();
        URL url = getClass().getResource("/com/example/employeeequipmentmanagementsystem/login/login.fxml");

        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene  = new Scene( root);
        StageSettings.setStage(stage, root);
        stage.setScene(scene);
        stage.show();

    }




    private void setHBoxStyle(int index, HBox hbox) {
        String baseStyle;

        if (index % 2 != 0) {
            baseStyle = "-fx-background-color: #cdcdcd;";
        } else {
            baseStyle = "-fx-background-color: white;";
        }

        hbox.setStyle(baseStyle);

    }


        private void addChildBasedOnObjectType(Object item, HBox hbox, VBox layout) {
            if (item instanceof Employee) {
                layout.getChildren().add(hbox);
            } else if (item instanceof Equipment) {
                layout.getChildren().add(hbox);
            } else if (item instanceof Training) {
                layout.getChildren().add(hbox);
            }
            else {
                handleInvalidDataType(item);
            }
        }

        private void handleInvalidControllerType(DataItemController specificController) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Invalid controller type: " + specificController.getClass().getName());
        }

        private void handleInvalidDataType(Object item) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Invalid data type: " + item.getClass().getName());
        }

        public void printDataInColumns(List<? extends Object> list, String fxmlItemFile,
                                       Class<? extends DataItemController> controllerClass, VBox layout) {
            for (int i = 0; i < list.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/" + fxmlItemFile));
                try {
                    HBox hbox = fxmlLoader.load();

                    DataItemController specificController = fxmlLoader.getController();
                    if(list.get(i) instanceof Employee){
                        fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/employee_item.fxml"));
                        EmployeeItemController employeeItemController = fxmlLoader.getController();
                        employeeItemController.setDashboardController(this);
                    }
                    if (controllerClass.isInstance(specificController)) {
                        specificController.setData(list.get(i));
                        setHBoxStyle(i, hbox);
                        addChildBasedOnObjectType(list.get(i), hbox, layout);
                    } else {
                        handleInvalidControllerType(specificController);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

}
