package com.hpms.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
	    return "MedicalRecord{" +
	            "id=" + id +
	            ", patient=" + (patient != null ? patient.getId() : "null") +
	            ", doctor=" + (doctor != null ? doctor.getId() : "null") +
	            ", date=" + date +
	            ", description='" + description + '\'' +
	            '}';
	}

}