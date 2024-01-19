module com.example.employeeequipmentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires jjwt;
    requires jjwt.impl;
    requires jjwt.api;
    requires jjwt.jackson;
    requires java.prefs;
    requires com.google.gson;
    requires javafx.base;

    opens com.example.employeeequipmentmanagementsystem.controller to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.model to com.google.gson;
    exports com.example.employeeequipmentmanagementsystem;
    exports com.example.employeeequipmentmanagementsystem.apiConnection;
    opens com.example.employeeequipmentmanagementsystem.controller.main to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.controller.controller to javafx.fxml;
}
