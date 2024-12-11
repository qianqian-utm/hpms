package model;

public class MedicalRecord {
	private Appointment appointment;
	private String description;
	
	// Constructors
	public MedicalRecord(Appointment appointment,String description) {
		this.appointment = appointment;
		this.description = description;
	}

	// Getters and setters
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
