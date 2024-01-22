package com.example.employeeequipmentmanagementsystem.model;

import java.util.UUID;

public class Employee {
    private UUID uuid;
    private String name;
    private String surname;
    private Integer daysToTraining;
    private Integer numberOfTools;


    private Employee() {
    }

    public UUID getEmployeeId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public Integer getDaysToTraining() {return daysToTraining;}

    public Integer getNumberOfTools() {return numberOfTools;}

    public static class Builder {
        private UUID uuid;
        private String name;
        private String surname;
        private Integer daysToTraining;
        private Integer numberOfTools;





        public Builder employeeId(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }
        public Builder daysToTraining(Integer daysToTraining) {
            this.daysToTraining = daysToTraining;
            return this;
        }
        public Builder numberOfTools(Integer numberOfTools) {
            this.numberOfTools = numberOfTools;
            return this;
        }
        public Employee build() {
            Employee employee = new Employee();
            employee.uuid = this.uuid;
            employee.name = this.name;
            employee.surname = this.surname;
            employee.daysToTraining=this.daysToTraining;
            employee.numberOfTools=this.numberOfTools;
            return employee;
        }


    }


}
