package com.example.employeeequipmentmanagementsystem.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Equipment {
    private UUID equipmentId;
    private String name;
    private BigDecimal price;
    private String description;
    private byte[] imageData;

    public UUID getEquipmentId() {
        return equipmentId;
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

    public byte[] getImageData() {
        return imageData;
    }

    public static class Builder {
        private UUID equipmentId;
        private String name;
        private BigDecimal price;
        private String description;
        private byte[] imageData;

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

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Equipment build() {
            Equipment equipment = new Equipment();
            equipment.equipmentId = this.equipmentId;
            equipment.name = this.name;
            equipment.price = this.price;
            equipment.description = this.description;
            equipment.imageData = this.imageData;
            return equipment;
        }
    }

}
