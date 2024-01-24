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
    requires java.logging;
    requires java.xml;

    opens com.example.employeeequipmentmanagementsystem.controller to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.model to com.google.gson;
    exports com.example.employeeequipmentmanagementsystem;
    exports com.example.employeeequipmentmanagementsystem.apiConnection;
    exports com.example.employeeequipmentmanagementsystem.controller.item.employeeDetails;
    opens com.example.employeeequipmentmanagementsystem.controller.main to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.controller.login to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.controller.item to javafx.fxml;
    opens com.example.employeeequipmentmanagementsystem.controller.item.employeeDetails to javafx.fxml;

}
