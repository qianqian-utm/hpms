package com.hpms.controller;

import com.hpms.model.Schedule;
import com.hpms.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManageScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/appointment_listing")
    public String manageSchedules(
            @RequestParam(value = "doctorName", required = false) String doctorName,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "patientName", required = false) String patientName,
            @RequestParam(value = "status", required = false) String status,
            Model model) {

        List<Schedule> schedules = scheduleService.searchSchedules(doctorName, date, time, patientName, status);
        model.addAttribute("schedules", schedules);
        return "appointment_listing";  // Return the view name corresponding to your JSP
    }
}
