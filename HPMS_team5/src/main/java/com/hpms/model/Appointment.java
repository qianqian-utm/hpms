package com.hpms.model;

import java.sql.Time;
import java.util.Date;

public class Appointment {
	private User doctor;
	private User patient;
	private Date appointmentDate;
	private Time startTime;
	private Time endTime;
	private int appointmentStatus;
	private int appointmentType;
	private TransactionRecord transactionRecord;

	// Constructors
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

	// Getters and setters

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
