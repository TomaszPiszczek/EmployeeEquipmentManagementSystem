package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Training;
import com.google.gson.reflect.TypeToken;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TrainingService {

    public static List<Training> getTrainingForEmployee(UUID employeeUUID){
        EquipmentApiConnection api = new EquipmentApiConnection();


        TypeToken<List<Training>> typeToken = new TypeToken<>() {};
        return api.callApi("employee/getEmployeeTrainings?EmployeeUUID=" + employeeUUID, "GET", null, typeToken.getType());

    }
    public static void removeTrainingFromEmployee(UUID employeeTrainingUUID){
        EquipmentApiConnection api = new EquipmentApiConnection();
        api.callApi("training/removeTrainingFromEmployee?employeeTrainingUUID=" + employeeTrainingUUID, "DELETE", null, null);

    }

    public static String createTraining(String name,String description){
       return EquipmentApiConnection.callApi("training/createTraining?name="+name+"&description="+description,"POST", HttpRequest.BodyPublishers.noBody(),String.class).toString();
    }
    //fixme remove training date
    public static void assignTrainingToEmployee(UUID employeeUUID, UUID trainignUUID, LocalDateTime assignDate){
        EquipmentApiConnection.callApi("employee/signEmployeeToTraining?employeeUUID="+employeeUUID+"&trainingUUID="+trainignUUID+"&trainingDate=2023-12-21T12:34:56&expireDate="+assignDate,"POST",HttpRequest.BodyPublishers.noBody(),null);
    }

    public static List<Training> getTrainings() {
        TypeToken<List<Training>> typeToken = new TypeToken<>() {};
        return EquipmentApiConnection.callApi("training/getTrainings","GET",null,typeToken.getType());
    }
}
