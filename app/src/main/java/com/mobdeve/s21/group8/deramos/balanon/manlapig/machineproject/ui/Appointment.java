// Appointment.java
package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

public class Appointment {
    private String date;
    private String location;
    private String service;

    public Appointment(String date, String location, String service) {
        this.date = date;
        this.location = location;
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getService() {
        return service;
    }
}
