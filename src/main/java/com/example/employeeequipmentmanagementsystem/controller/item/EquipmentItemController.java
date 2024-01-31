package com.example.employeeequipmentmanagementsystem.controller.item;

import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import com.example.employeeequipmentmanagementsystem.controller.main.employee.AddEmployeeController;
import com.example.employeeequipmentmanagementsystem.controller.main.tools.ChangeDateToolController;
import com.example.employeeequipmentmanagementsystem.controller.main.tools.CreateToolFormController;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentItemController implements Initializable, DataItemController {

    @FXML
    private Label description;
    @FXML
    private HBox column;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label date;

    private DashboardController dashboardController;




    private Equipment equipment;
    private boolean isSelected = false;

    public void setData(Object data) {

        if (data instanceof Equipment equipment) {


            this.equipment = equipment;
            updateItemStyle();
            description.setText(equipment.getDescription());

            if (equipment.getImageData() != null) {
                String imageDataString = equipment.getImageData();
                byte[] imageDataBytes = imageDataString.getBytes();

                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageDataBytes);
                Image img = new Image(inputStream);
                image.setImage(img);
            }
            date.setText(formatAssignDate(equipment.getServiceDate().toString()));
            name.setText(equipment.getName());
            if (equipment.getPrice() != null) {
                price.setText(equipment.getPrice().toString());
            }
        }
    }
    private String formatAssignDate(String assignDate) {
        if (assignDate != null && assignDate.contains("T")) {
            return assignDate.split("T")[0];
        } else {
            return assignDate;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dashboardController = DashboardController.getInstance();


        column.setOnMouseClicked(event -> handleItemClick());
    }
    @FXML
    void changeData(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/tools/update/change_tool_service_date_form.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Zmien date przegladu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            ChangeDateToolController changeDateToolController = fxmlLoader.getController();
            changeDateToolController.setStage((Stage) root.getScene().getWindow());
            changeDateToolController.setEquipmentUUID(equipment.getEquipmentId());
            changeDateToolController.setDescription(equipment.getDescription());
            changeDateToolController.setName(equipment.getName());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleItemClick() {
        isSelected = !isSelected;
        updateItemStyle();
    }

    private void updateItemStyle() {
        if (isSelected) {
            column.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            dashboardController.addToEquipmentUUIDList(equipment.getEquipmentId());
        } else {
            column.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
            dashboardController.removeFromEquipmentUUIDList(equipment.getEquipmentId());
        }
    }
}
