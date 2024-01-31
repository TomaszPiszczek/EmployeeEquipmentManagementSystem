package com.example.employeeequipmentmanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class Equipment {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageData;
    private String assignDate;
    private String serviceDate;

    public UUID getEquipmentId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageData() {
        return imageData;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public static class Builder {
        private UUID equipmentId;
        private String name;
        private BigDecimal price;
        private String description;
        private String imageData;
        private String assignDate;
        private String serviceDate; // Added serviceDate field

        public Builder() {
            this.equipmentId = UUID.randomUUID();
        }

        public Builder equipmentId(UUID equipmentId) {
            this.equipmentId = equipmentId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder assignDate(String assignDate) {
            this.assignDate = assignDate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageData(String imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder serviceDate(String serviceDate) {
            this.serviceDate = serviceDate;
            return this;
        }

        public Equipment build() {
            Equipment equipment = new Equipment();
            equipment.uuid = this.equipmentId;
            equipment.name = this.name;
            equipment.price = this.price;
            equipment.description = this.description;
            equipment.imageData = this.imageData;
            equipment.assignDate = this.assignDate;
            equipment.serviceDate = this.serviceDate;
            return equipment;
        }
    }
}
