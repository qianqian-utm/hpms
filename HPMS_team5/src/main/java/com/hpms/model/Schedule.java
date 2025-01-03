package com.hpms.model;

import java.time.LocalDate;

public class Schedule {
    private int id;
    private String doctorName;
    private String Date;
    private String time;
    private String patientName;
    private String status;

    // Constructor
    public Schedule(int id, String doctorName, String Date, String time, String patientName, String status) {
        this.id = id;
        this.doctorName = doctorName;
        this.Date = Date;
        this.time = time;
        this.patientName = patientName;
        this.status = status;
    }

    // Getters and setters (if needed)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
