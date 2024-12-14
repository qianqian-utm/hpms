package com.hpms.model;

public class User {
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private String phone_number;
	private int role; // 1=admin, 2=patient
	private int gender; // 1=male, 2=female
	
// Constructors
	public User() {
		
	}

	public User( String first_name, String last_name, String email, String password, String phone_number, int role, int gender) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.phone_number = phone_number;
		this.role = role;
		this.gender = gender;
	}
	
	public User(String first_name, String last_name, String email, String phone_number, int role, int gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.role = role;
        this.gender = gender;
    }

//	Getters and Setters
	public String getFirstName() {
		return first_name;
	}
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	public String getLastName() {
		return last_name;
	}
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phone_number;
	}
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
}
