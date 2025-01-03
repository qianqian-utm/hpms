package com.hpms.repository;

import com.hpms.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctorNameAndAvailableDateGreaterThanEqualAndIsAvailableTrue(
        String doctorName, LocalDate date);
}

