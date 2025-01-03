package com.hpms.model;

import com.hpms.entity.DoctorAvailability;
import com.hpms.repository.DoctorAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorAvailabilityService {
    
    @Autowired
    private DoctorAvailabilityRepository repository;
    
    public List<DoctorAvailability> getAvailableDates(String doctorName) {
        return repository.findByDoctorNameAndAvailableDateGreaterThanEqualAndIsAvailableTrue(
            doctorName, LocalDate.now());
    }
    
    public void setAvailability(String doctorName, LocalDate date, boolean isAvailable) {
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorName(doctorName);
        availability.setAvailableDate(date);
        availability.setAvailable(isAvailable);
        repository.save(availability);
    }
}