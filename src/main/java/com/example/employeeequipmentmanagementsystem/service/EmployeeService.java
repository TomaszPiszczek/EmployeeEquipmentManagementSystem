package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.UUID;
import java.util.prefs.Preferences;

public class EmployeeService {
    //fixme java.lang.NullPointerException: Cannot invoke "com.google.gson.JsonObject.get(String)" because "jsonResponse" is null
    public static List<Employee> getEmployeesDTO() {


        TypeToken<List<Employee>> typeToken = new TypeToken<>() {
        };
        return EquipmentApiConnection.callApi("employee/getEmployees", "GET", null, typeToken.getType());

    }

    public static Employee getEmployee(UUID employeeUUID) {
        EquipmentApiConnection api = new EquipmentApiConnection();

        TypeToken<Employee> typeToken = new TypeToken<>() {
        };
        return api.callApi("employee/getEmployee?employeeUUID=" + employeeUUID, "GET", null, typeToken.getType());

    }

    public static void removeEmployee(UUID employeeUUID) {
        EquipmentApiConnection.callApi("employee/removeEmployee?employeeUUID=" + employeeUUID, "DELETE", null, String.class);
    }
}
