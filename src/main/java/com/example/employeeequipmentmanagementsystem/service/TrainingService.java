package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.UUID;

public class TrainingService {

    public static List<Training> getTrainingForEmployee(UUID employeeUUID){
        EquipmentApiConnection api = new EquipmentApiConnection();


        TypeToken<List<Training>> typeToken = new TypeToken<>() {};
        return api.callApi("employee/getEmployeeTrainings?EmployeeUUID=" + employeeUUID, "GET", null, typeToken.getType());

    }
}
