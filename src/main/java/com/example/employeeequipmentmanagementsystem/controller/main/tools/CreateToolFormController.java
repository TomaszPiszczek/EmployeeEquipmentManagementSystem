package com.example.employeeequipmentmanagementsystem.controller.main.tools;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

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
        // Use DoubleStringConverter to allow for large numbers and decimals
        price.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0.0, change -> {
            String newText = change.getControlNewText();
            // Regular expression to allow valid double input
            if (newText.matches("-?\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void createTool(ActionEvent event) {
        LocalDate selectedDate = date.getValue();
        LocalTime midnight = LocalTime.MIDNIGHT;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = "";

        if (selectedDate != null) {
            LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, midnight);
            formattedDateTime = selectedDateTime.format(formatter);
        }

        String apiResponse = EquipmentApiConnection.callApi(
                "equipment/createEquipment?image=null&price=" + price.getText() +
                        "&name=" + name.getText() +
                        "&description=" + description.getText() +
                        "&serviceDate=" + formattedDateTime,
                "POST", HttpRequest.BodyPublishers.noBody(), String.class).toString();

        if (apiResponse.contains("Duplicate")) {
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
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
