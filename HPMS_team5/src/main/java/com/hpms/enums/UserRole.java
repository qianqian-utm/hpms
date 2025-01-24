package com.hpms.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");
    
    private String value;
    
    UserRole(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}