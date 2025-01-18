package com.hpms.controller;

import com.hpms.model.MedicalRecord;
import com.hpms.model.User;
import com.hpms.service.MedicalRecordService;
import com.hpms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalRecords";
    }

    @GetMapping("/add")
    public String addMedicalRecordForm(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        model.addAttribute("patients", userService.getPatients());
        model.addAttribute("doctors", userService.getDoctors());
        return "addMedicalRecord";
    }

    @PostMapping("/add")
    public String addMedicalRecord(@Valid @ModelAttribute MedicalRecord medicalRecord, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", userService.getPatients());
            model.addAttribute("doctors", userService.getDoctors());
            return "addMedicalRecord";
        }
        medicalRecordService.addMedicalRecord(medicalRecord);
        return "redirect:/medicalRecords";
    }

    @GetMapping("/edit/{id}")
    public String editMedicalRecordForm(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        if (medicalRecord == null) {
            return "error"; // Or redirect to an error page
        }
        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("patients", userService.getPatients());
        model.addAttribute("doctors", userService.getDoctors());
        return "editMedicalRecord";
    }

    @PostMapping("/edit")
    public String editMedicalRecord(@Valid @ModelAttribute MedicalRecord medicalRecord, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patients", userService.getPatients());
            model.addAttribute("doctors", userService.getDoctors());
            return "editMedicalRecord";
        }
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return "redirect:/medicalRecords";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return "redirect:/medicalRecords";
    }
}
