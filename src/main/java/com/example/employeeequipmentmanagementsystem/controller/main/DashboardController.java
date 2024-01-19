package com.example.employeeequipmentmanagementsystem.controller.main;

import com.example.employeeequipmentmanagementsystem.controller.StageSettings;
import com.example.employeeequipmentmanagementsystem.controller.controller.EmployeeItemController;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.service.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashboardController implements Initializable {

    @FXML
    private VBox employeeLayout;
    @FXML
    private VBox equipmentLayout;
    @FXML
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





        //fixme 1:21
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initalizeData();
    }

    private void initalizeData() {
        employeeLayout.getChildren().clear();
        employeeLayout.setSpacing(1);
        equipmentLayout.setSpacing(1);
        List<Employee> employeeList = EmployeeService.getEmployeesDTO();
        for (int i = 0; i < employeeList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/employee_item.fxml"));
            try {
                HBox hbox = fxmlLoader.load();
                EmployeeItemController EIC = fxmlLoader.getController();
                EIC.setData(employeeList.get(i));

                if (i % 2 == 0) {
                    hbox.setStyle("-fx-background-color: white"); // Light gray

                } else {
                    hbox.setStyle("-fx-background-color: #cdcdcd;"); // Light gray
                }

                employeeLayout.getChildren().add(hbox);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    }

    /* List<Employee> employees = new ArrayList<>();
       for (int i = 0; i < employees.size(); i++) {
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/employee_item.fxml"));
           try {
               HBox hbox = fxmlLoader.load();
               EmployeeItemController EIC = fxmlLoader.getController();
               EIC.setData(employees.get(i));

               if (i % 2 == 0) {
                   hbox.setStyle("-fx-background-color: white"); // Light gray

               } else {
                   hbox.setStyle("-fx-background-color: #cdcdcd;"); // Light gray
               }

               employeeLayout.getChildren().add(hbox);

           } catch (IOException ex) {
               ex.printStackTrace();
           }*/
    public void switchForm(ActionEvent event){
        if(event.getSource() == scene_employee){
            tools_stage.setVisible(false);
            employees_stage.setVisible(false);
            employees_stage.setVisible(true);
            initalizeData();
        }
        if(event.getSource() == scene_tools){
            employees_stage.setVisible(false);
            employees_stage.setVisible(false);
            tools_stage.setVisible(true);
            initalizeData();

        }
        if(event.getSource() == scene_cars){  //todo cars stage
            employees_stage.setVisible(false);
            tools_stage.setVisible(false);
            employees_stage.setVisible(false);
            initalizeData();

        }
    }
    public void close(){
        System.exit(0);
    }
    public void minimise(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public void logout() throws BackingStoreException, IOException {
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

}
