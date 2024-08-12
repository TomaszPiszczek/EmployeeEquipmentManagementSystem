package com.example.employeeequipmentmanagementsystem.controller.main.tools;

import com.example.employeeequipmentmanagementsystem.controller.login.LoginController;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.service.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class AssignToolController implements Initializable {
    private static AssignToolController instance;

    public static AssignToolController getInstance() {
        if (instance == null) {
            instance = new AssignToolController();
        }
        return instance;
    }

    @FXML
    private HBox employeeColumn;

    @FXML
    private VBox employeeLayout;

    private DashboardController dashboardController;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardController = DashboardController.getInstance();


        List<Employee> employees = new ArrayList<>();
        employees = EmployeeService.getEmployeesDTO();
        dashboardController.printDataInColumns(employees,"assign_employee_item.fxml", AssignEmployeeItemController.class,employeeLayout);
    }





    @FXML
    void assignTools(MouseEvent event) {
        dashboardController.assignToolsToEmployees();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Narzędzia zostały przypisane.", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }


}
