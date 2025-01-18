package com.hpms.model;

import javax.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key for the appointment

    @ManyToOne(fetch = FetchType.LAZY)  // Many appointments can belong to one doctor
    @JoinColumn(name = "doctor_id", nullable = false)  // Foreign key linking to User table for doctor
    private User doctor;  // Reference to the User entity as the doctor

    @ManyToOne(fetch = FetchType.LAZY)  // Many appointments can belong to one patient
    @JoinColumn(name = "patient_id", nullable = false)  // Foreign key linking to User table for patient
    private User patient;  // Reference to the User entity as the patient

    @Temporal(TemporalType.DATE)  // Mapping the date for the appointment
    @Column(name = "appointment_date", nullable = false)
     private Date appointmentDate;

    @Column(name = "start_time", nullable = false)  // Time when the appointment starts
     private Time startTime;

    @Column(name = "end_time", nullable = false)  // Time when the appointment ends
    private Time endTime;

    @Column(name = "appointment_status", nullable = false)  // Status of the appointment (e.g., 'new', 'review')
    private int appointmentStatus;

    @Column(name = "appointment_type", nullable = false)  // Type of appointment (e.g., consultation, checkup)
    private int appointmentType;

    @ManyToOne(fetch = FetchType.LAZY)  // Many appointments can belong to one transaction record
    @JoinColumn(name = "transaction_record_id")  // Foreign key to the transaction record table
    private TransactionRecord transactionRecord;  // Link to the transaction record if needed

    // Default constructor
    public Appointment() {}

    // Constructor with all fields, including transactionRecord
    public Appointment(User doctor, User patient, Date appointmentDate, Time startTime, Time endTime, 
                       int appointmentStatus, int appointmentType, TransactionRecord transactionRecord) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentType = appointmentType;
        this.transactionRecord = transactionRecord;
    }

    // Constructor without transactionRecord (if optional)
    public Appointment(User doctor, User patient, Date appointmentDate, Time startTime, Time endTime, 
                       int appointmentStatus, int appointmentType) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentType = appointmentType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(int appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public int getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(int appointmentType) {
        this.appointmentType = appointmentType;
    }

    public TransactionRecord getTransactionRecord() {
        return transactionRecord;
    }

    public void setTransactionRecord(TransactionRecord transactionRecord) {
        this.transactionRecord = transactionRecord;
    }
}
