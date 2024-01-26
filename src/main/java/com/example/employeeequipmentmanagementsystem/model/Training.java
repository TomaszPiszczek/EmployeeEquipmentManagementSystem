package com.example.employeeequipmentmanagementsystem.model;

import java.util.UUID;

public class Training {
    private UUID uuid;
    private String trainingName;
    private String description;
    private String expireDate;

    public UUID getTrainingId() {
        return uuid;
    }

    public String getName() {
        return trainingName;
    }

    public String getDescription() {
        return description;
    }

    public String getExpireDate() {
        return expireDate;
    }
}
