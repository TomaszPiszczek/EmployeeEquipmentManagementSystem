package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.UUID;
import java.util.prefs.Preferences;

public class EquipmentService {
    public static List<Equipment> getEquipment(){
        EquipmentApiConnection api = new EquipmentApiConnection();


        TypeToken<List<Equipment>> typeToken = new TypeToken<>() {};
        return api.callApi("equipment/getEquipment", "GET", null, typeToken.getType());

    }
    public static List<Equipment> getEquipmentForEmployee(UUID employeeUUID){
        EquipmentApiConnection api = new EquipmentApiConnection();
        TypeToken<List<Equipment>> typeToken = new TypeToken<>() {};
        return api.callApi("equipment/getEmployeeEquipment?employeeUUID=" + employeeUUID, "GET", null, typeToken.getType());

    }
    //public static void removeEquipmentFromEmployee(UUID employeeUUID,)

}
