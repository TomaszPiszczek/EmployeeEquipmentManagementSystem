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

    opens com.example.employeeequipmentmanagementsystem.controller to javafx.fxml;
    exports com.example.employeeequipmentmanagementsystem;
    exports com.example.employeeequipmentmanagementsystem.apiConnection;
}
