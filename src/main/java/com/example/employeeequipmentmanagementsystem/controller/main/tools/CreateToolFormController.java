package com.example.employeeequipmentmanagementsystem.controller.main.tools;


import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.service.EquipmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateToolFormController implements Initializable {

    @FXML
    private TextField description;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    private Stage stage;

    @FXML
    private DatePicker date;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        price.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void createTool(ActionEvent event) {

        LocalDate selectedDate = date.getValue();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, midnight);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = selectedDateTime.format(formatter);


        if(EquipmentApiConnection.callApi("equipment/createEquipment?image=null&price="+price.getText()+"&name="+name.getText()+"&description="+ description.getText()+"&serviceDate="+formattedDateTime,"POST", HttpRequest.BodyPublishers.noBody(),String.class)
                .toString().contains("Duplicate")){
            showAlert("Podane narzędzie już istnieje");
        }
            closeForm();
    }
    private void closeForm() {
        if (stage != null) {
            stage.close();
        }
    }
    private static void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(content);

        alert.showAndWait();
    }

}
