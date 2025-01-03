package com.hpms.controller;


import com.hpms.model.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.stream.Collectors;

@Controller
public class DoctorAvailabilityController {
    
    @Autowired
    private DoctorAvailabilityService service;
    
    @GetMapping("/doctor-availability")
    public String showAvailability(@RequestParam String doctorName, Model model) {
        model.addAttribute("availableDates", 
            service.getAvailableDates(doctorName)
                  .stream()
                  .map(da -> da.getAvailableDate())
                  .collect(Collectors.toList()));
        return "doctor-availability";  // This refers to your JSP file
    }
}