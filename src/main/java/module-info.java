module com.example.employeeequipmentmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.employeeequipmentmanagementsystem to javafx.fxml;
    exports com.example.employeeequipmentmanagementsystem;
}