package com.hpms.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(length = 2000)
	private String description;

	// Constructors, getters, and setters
	public MedicalRecord() {
	}

	public MedicalRecord(Appointment appointment, Date date, String description) {
		this.appointment = appointment;
		this.date = date;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
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
		return "MedicalRecord{" + "id=" + id + ", appointment=" + (appointment != null ? appointment.getId() : "null")
				+ ", date=" + date + ", description='" + description + '\'' + '}';
	}

}