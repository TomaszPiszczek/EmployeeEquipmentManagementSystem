package com.example.employeeequipmentmanagementsystem.controller;

import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class DashboardController implements Initializable {

    @FXML
    private VBox employeeLayout;
    @FXML
    private VBox equipmentLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // employeeLayout.setSpacing(1);
        equipmentLayout.setSpacing(1);
        //fixme add empployees from api
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

            List<Equipment> equipmentList = new ArrayList<>(equipments());
            for (int i = 0; i < equipmentList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/items/equipment_item.fxml"));
                try {
                    HBox hbox = fxmlLoader.load();
                    EquipmentItemController EIC = fxmlLoader.getController();
                    EIC.setData(equipmentList.get(i));

                    if (i % 2 == 0) {
                        hbox.setStyle("-fx-background-color: white"); // Light gray

                    } else {
                        hbox.setStyle("-fx-background-color: #cdcdcd;"); // Light gray
                    }

                    equipmentLayout.getChildren().add(hbox);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
  /*  private List<Employee> employees(){
        List<Employee> ls = new ArrayList<>();
        Employee employee = new Employee.Builder()
                .employeeId(UUID.randomUUID())
                .name("ADAM")
                .surname("NOWAK")
                .daysToTraining(32)
                .numberOfTools(10)
                .build();
        for (int i = 0; i < 20; i++) {
            ls.add(employee);
        }


        return ls;
    }*/
  private List<Equipment> equipments(){
      List<Equipment> ls = new ArrayList<>();


      for (int i = 0; i < 20; i++) {
          Equipment equipment = new Equipment.Builder()
                  .equipmentId(UUID.randomUUID())
                  .description("desc")
                  .name("name")
                  .price(BigDecimal.valueOf(Math.random()*10+4))
                  .build();
          ls.add(equipment);
      }


      return ls;
  }
}
