package com.example.employeeequipmentmanagementsystem.model;

import java.util.UUID;

public class Training {
    private UUID trainingId;
    private String name;
    private String description;
    private String expireDate;

    public UUID getTrainingId() {
        return trainingId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getExpireDate() {
        return expireDate;
    }
}
