package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.prefs.Preferences;

public class EmployeeService {
        //fixme java.lang.NullPointerException: Cannot invoke "com.google.gson.JsonObject.get(String)" because "jsonResponse" is null
    public static List<Employee> getEmployeesDTO(){
        EquipmentApiConnection api = new EquipmentApiConnection();
        api.login("correct@correct.com","password123");
        Preferences userPreferences = Preferences.userRoot();


        TypeToken<List<Employee>> typeToken = new TypeToken<>() {};
         return api.callApi("employee/getEmployees", "GET", null, userPreferences.get("token", ""), typeToken.getType());

    }
}