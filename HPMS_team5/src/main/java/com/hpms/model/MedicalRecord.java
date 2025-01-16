package com.hpms.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(length = 2000)
    private String description;

    // Constructors, getters, and setters
    public MedicalRecord() {
    }

    public MedicalRecord(User patient, User doctor, Date date, String description) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.description = description;
    }

    // Getters and setters
    // ...
}
