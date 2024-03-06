package com.example.employeeequipmentmanagementsystem.controller.main;

import com.example.employeeequipmentmanagementsystem.controller.StageSettings;
import com.example.employeeequipmentmanagementsystem.controller.item.*;
import com.example.employeeequipmentmanagementsystem.controller.main.employee.AddEmployeeController;
import com.example.employeeequipmentmanagementsystem.controller.main.employee.AddToolFormController;
import com.example.employeeequipmentmanagementsystem.controller.main.employee.AssignTrainingController;
import com.example.employeeequipmentmanagementsystem.controller.main.tools.AssignEmployeeItemController;
import com.example.employeeequipmentmanagementsystem.controller.main.tools.CreateToolFormController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.example.employeeequipmentmanagementsystem.service.EmployeeService;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import com.example.employeeequipmentmanagementsystem.service.TrainingService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashboardController implements Initializable {
    

    private static DashboardController instance;
    @FXML
    private Label userName;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private VBox employeeLayout;
    @FXML
    private VBox equipmentLayout;
    @FXML
    private VBox trainingLayoutDetails;
    @FXML
    private VBox equipmentLayoutDetails;
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane employee_detail_stage;
    @FXML
    private AnchorPane employees_stage;

    @FXML
    private Button logout;

    @FXML
    private Button scene_cars;
    @FXML
    private Button scene_employee;
    @FXML
    private Button scene_tools;
    @FXML
    private AnchorPane tools_stage;

    private UUID employeeUUID;
    ///
    ///assign TOOL
    ///
    private Set<UUID> employeeUUIDList = new HashSet<>();
    private Set<UUID> equipmentUUIDList = new HashSet<>();


    private DashboardController() {
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            instance = new DashboardController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEmployeeData();
        Preferences userPref = Preferences.userRoot();
        this.userName.setText(userPref.get("email",""));
    }

    public UUID getEmployeeUUID() {
        return employeeUUID;
    }

    public void setEmployeeUUID(UUID employeeUUID) {
        this.employeeUUID = employeeUUID;
    }

    private void initializeEmployeeData() {

        Task<List<Employee>> task = new Task<>() {
            @Override
            protected List<Employee> call()  {
                return EmployeeService.getEmployeesDTO();
            }
            @Override
            protected void succeeded() {
                clearChildren(employeeLayout);
                employeeLayout.setSpacing(1);
                List<Employee> employeeList = getValue();
                Platform.runLater(() -> printDataInColumns(employeeList, "employee_item.fxml", EmployeeItemController.class, employeeLayout));
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }





    private void initializeEquipmentData() {
        Task<List<Equipment>> task = new Task<>() {
            @Override
            protected List<Equipment> call()  {
                return EquipmentService.getEquipment();

            }
            @Override
            protected void succeeded() {
                clearChildren(equipmentLayout);
                equipmentLayout.setSpacing(1);
                List<Equipment> equipmentList = getValue();
                Platform.runLater(() -> printDataInColumns(equipmentList, "equipment_item.fxml", EquipmentItemController.class, equipmentLayout));
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    private void initializeEquipmentDetails(UUID employeeUUID) {
        clearChildren(equipmentLayoutDetails);
        equipmentLayoutDetails.setSpacing(1);

        Employee employee = EmployeeService.getEmployee(employeeUUID);
        name.setText(employee.getName());
        surname.setText(employee.getSurname());

        List<Equipment> equipmentList = EquipmentService.getEquipmentForEmployee(employeeUUID);
        printDataInColumns(equipmentList, "equipment_item_details.fxml", EquipmentItemDetailsController.class, equipmentLayoutDetails);
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
        initializeEmployeeData();
        tools_stage.setVisible(false);
        employee_detail_stage.setVisible(false);
        employees_stage.setVisible(true);
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

    public void switchToEmployeeDetailStage(UUID uuid) {
        employees_stage.setVisible(false);
        employee_detail_stage.setVisible(true);
        initializeTrainingDetails(uuid);
        initializeEquipmentDetails(uuid);
        AssignTrainingController.getInstance().setEmployeeUUID(uuid);
        AddToolFormController.getInstance().setEmployeeUUID(uuid);
    }

    @FXML
    private void switchForm(ActionEvent event) {
        switchForms(event, null);
    }

    public void switchForms(ActionEvent event, UUID uuid) {
        if (event.getSource() == scene_employee) {
            switchToEmployeesStage();
        } else if (event.getSource() == scene_tools) {
            switchToToolsStage();
        } else if (event.getSource() == scene_cars) {
            switchToCarsStage();
        } else if (event.getSource() instanceof Node sourceNode) {
            if ("employee_more".equals(sourceNode.getId())) {
                switchToEmployeeDetailStage(uuid);

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

        assert url != null;
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        StageSettings.setStage(stage, root);
        stage.setScene(scene);
        stage.show();

    }

    private void setHBoxStyle(HBox hbox) {
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
    }

    private void addChildBasedOnObjectType(Object item, HBox hbox, VBox layout) {
        if (item instanceof Employee) {
            layout.getChildren().add(hbox);
        } else if (item instanceof Equipment) {
            layout.getChildren().add(hbox);
        } else if (item instanceof Training) {
            layout.getChildren().add(hbox);
        } else {
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

    public void printDataInColumns(List<?> list, String fxmlItemFile, Class<? extends DataItemController> controllerClass, VBox layout) {
        for (Object o : list) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/" + fxmlItemFile));
            try {
                HBox hbox = fxmlLoader.load();

                DataItemController specificController = fxmlLoader.getController();

                if (o instanceof Employee) {

                    if (fxmlItemFile.contains("assign")) {
                        fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/assign_employee_item.fxml"));

                        AssignEmployeeItemController assignEmployeeItemController = fxmlLoader.getController();
                    } else {
                        fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/employee_item.fxml"));
                    }

                }
                if (o instanceof Equipment) {
                    fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/equipment_item.fxml"));
                }

                if (controllerClass.isInstance(specificController)) {
                    specificController.setData(o);
                    setHBoxStyle(hbox);
                    addChildBasedOnObjectType(o, hbox, layout);
                } else {
                    handleInvalidControllerType(specificController);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //
    // TOOLS CONTROLLER
    //
    @FXML
    void addTool(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/tools/createTool/create_tool_form.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj Narzędzie");
            stage.initModality(Modality.APPLICATION_MODAL); // Ustawia okno jako modalne (zamyka poprzednie okno, dopóki nie zostanie zamknięte)
            stage.setScene(new Scene(root));

            CreateToolFormController createToolFormController = fxmlLoader.getController();
            createToolFormController.setStage((Stage) root.getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        switchToToolsStage();

    }

    public void assignToolsToEmployees() {
        EquipmentService.assignEquipmentsToEmployees(employeeUUIDList, equipmentUUIDList, "2024-01-26T14:13:33");

        employeeUUIDList = new HashSet<>();
        equipmentUUIDList = new HashSet<>();

    }

    public void addToEmployeeUUIDList(UUID employeeUUID) {
        employeeUUIDList.add(employeeUUID);
    }

    public void removeFromEmployeeUUIDList(UUID employeeUUID) {
        employeeUUIDList.remove(employeeUUID);
    }
    ///
    ///REMOVE TOOL
    ///

    @FXML
    void assignTool(MouseEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/tools/assignTool/assign_tool.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj Narzędzie");
            stage.initModality(Modality.APPLICATION_MODAL); // Ustawia okno jako modalne (zamyka poprzednie okno, dopóki nie zostanie zamknięte)
            stage.setScene(new Scene(root));


            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeTool(MouseEvent event) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Potwierdzenie usunięcia narzędzi");
        confirmationAlert.setHeaderText("Usunięcie tego narzędzia spowoduje usunięcie go z wyposażenia pracowników.");
        confirmationAlert.setContentText("Czy na pewno chcesz kontynuować?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (UUID uuid : equipmentUUIDList) {
                EquipmentService.removeEquipment(uuid);
            }

            equipmentUUIDList = new HashSet<>();
            switchToToolsStage();
        }
    }

    //
    // Employee CONTROLLER
    //
    @FXML
    void removeEmployee(MouseEvent event) {
        for (UUID uuid : employeeUUIDList) {
            EmployeeService.removeEmployee(uuid);
        }
        employeeUUIDList = new HashSet<>();
        switchToEmployeesStage();
    }

    @FXML
    void editEmployee(MouseEvent event) {

    }

    @FXML
    void addEmployee(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/employee/create_employee/create_employee_form.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj Pracownika");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            AddEmployeeController addEmployeeController = fxmlLoader.getController();
            addEmployeeController.setStage((Stage) root.getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        switchToEmployeesStage();
    }

    ///
    ///EmployeeDetails Controller
    ///

    @FXML
    void AddTraining(MouseEvent event) {
        try {
            AssignTrainingController assignTrainingController = AssignTrainingController.getInstance();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/employee/training/assign_training.fxml"));
            loader.setController(assignTrainingController);


            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj badanie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));


            assignTrainingController.setStage((Stage) root.getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void AddTool(MouseEvent event) {
        try {
            AddToolFormController addToolFormController = AddToolFormController.getInstance();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/employee/tool/add_tool_to_employee.fxml"));
            loader.setController(addToolFormController);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Przypisz narzedzie");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));


            addToolFormController.setStage((Stage) root.getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addToEquipmentUUIDList(UUID equipmentUUID) {
        equipmentUUIDList.add(equipmentUUID);
    }

    public void removeFromEquipmentUUIDList(UUID equipmentUUID) {
        equipmentUUIDList.remove(equipmentUUID);
    }


}
