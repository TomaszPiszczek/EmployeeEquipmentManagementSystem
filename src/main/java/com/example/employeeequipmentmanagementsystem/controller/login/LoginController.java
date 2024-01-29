package com.example.employeeequipmentmanagementsystem.controller.login;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.controller.StageSettings;
import com.example.employeeequipmentmanagementsystem.controller.main.DashboardController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {




    @FXML
    private Button close;

    @FXML
    private TextField emailField;

    @FXML
    private Button login;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private PasswordField password;
    private DashboardController dashboardController;
    @FXML
    private void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Preferences userPref = Preferences.userRoot();
        if(EquipmentApiConnection.isTokenValid(userPref.get("token",""))){
            //changeScene();
            Platform.runLater(this::changeScene);

        }
    }

    public void loginAction() {
        EquipmentApiConnection equipmentApiConnection = new EquipmentApiConnection();
        String email = emailField.getText();
        String enteredPassword = password.getText();

        equipmentApiConnection.login(email, enteredPassword);
        Preferences userPreferences = Preferences.userRoot();

        if (!equipmentApiConnection.isTokenValid(userPreferences.get("token", ""))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Blędne hasło lub email");
            alert.showAndWait();
            return;
        }
        changeScene();
    }
    private void changeScene(){
        login.getScene().getWindow().hide();
        dashboardController = DashboardController.getInstance();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/employeeequipmentmanagementsystem/main/dashboard.fxml"));
        loader.setController(dashboardController);
        System.out.println("LOGIN CONTROLLER  " + dashboardController.hashCode());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);
        StageSettings.setStage(stage, root);

        root.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        stage.setScene(scene);
        stage.show();
    }
}
