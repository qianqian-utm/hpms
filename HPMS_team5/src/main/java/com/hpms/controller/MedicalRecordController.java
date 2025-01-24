package com.hpms.controller;

import com.hpms.dto.MedicalRecordDTO; // Ensure this exists
import com.hpms.model.MedicalRecord;
import com.hpms.service.MedicalRecordService;
import com.hpms.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final UserService userService;

    private static final String PATIENTS_ATTRIBUTE = "patients";
    private static final String DOCTORS_ATTRIBUTE = "doctors";
    private static final String REDIRECT_MEDICAL_RECORDS = "redirect:/medicalRecords";

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService, UserService userService) {
        this.medicalRecordService = medicalRecordService;
        this.userService = userService;
    }

    @GetMapping
    public String viewMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalRecords";
    }

    @GetMapping("/add")
    public String addMedicalRecordForm(Model model) {
        model.addAttribute(PATIENTS_ATTRIBUTE, userService.getPatients());
        model.addAttribute(DOCTORS_ATTRIBUTE, userService.getDoctors());
        return "addMedicalRecord";
    }

    @PostMapping("/add")
    public String addMedicalRecord(@ModelAttribute MedicalRecordDTO medicalRecordDTO, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PATIENTS_ATTRIBUTE, userService.getPatients());
            model.addAttribute(DOCTORS_ATTRIBUTE, userService.getDoctors());
            return "addMedicalRecord";
        }
        medicalRecordService.addMedicalRecord(medicalRecordDTO);
        return REDIRECT_MEDICAL_RECORDS;
    }
}
