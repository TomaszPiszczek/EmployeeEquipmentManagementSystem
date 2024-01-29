package com.example.employeeequipmentmanagementsystem.service;

import com.example.employeeequipmentmanagementsystem.apiConnection.EquipmentApiConnection;
import com.example.employeeequipmentmanagementsystem.model.Employee;
import com.example.employeeequipmentmanagementsystem.model.Equipment;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.prefs.Preferences;

public class EquipmentService {
    public static List<Equipment> getEquipment(){


        TypeToken<List<Equipment>> typeToken = new TypeToken<>() {};
        return EquipmentApiConnection.callApi("equipment/getEquipment", "GET", null, typeToken.getType());

    }
    public static List<Equipment> getEquipmentForEmployee(UUID employeeUUID){
        TypeToken<List<Equipment>> typeToken = new TypeToken<>() {};
        return EquipmentApiConnection.callApi("equipment/getEmployeeEquipment?employeeUUID=" + employeeUUID, "GET", null, typeToken.getType());

    }
    //public static void removeEquipmentFromEmployee(UUID employeeUUID,)

    public static void createEquipment(BigDecimal price,String name,String description){
        EquipmentApiConnection.callApi("equipment/createEquipment?image=null&price="+price+"&name="+name+"&description="+ description,"POST", HttpRequest.BodyPublishers.noBody(),null);
    }
    public static void removeEquipment(UUID equipmentUUID){
        EquipmentApiConnection.callApi("equipment/removeEquipment?equipmentUUID="+equipmentUUID,"DELETE",null,null);
    }
    public static String assignEquipmentsToEmployees(Set<UUID> employeeUUIDList,Set<UUID> equipmentUUIDList,String assignTime){
        JSONObject jsonBody = new JSONObject();
        JSONArray employeeArray = new JSONArray(employeeUUIDList);
        jsonBody.put("employeeUUID", employeeArray);

        JSONArray equipmentArray = new JSONArray(equipmentUUIDList);
        jsonBody.put("equipmentUUID", equipmentArray);

        jsonBody.put("assignDate", assignTime);

        String requestBody = jsonBody.toString();
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(requestBody);

       return   EquipmentApiConnection.callApi("equipment/signEquipmentsToEmployees", "POST", bodyPublisher, String.class).toString();
    }


}
