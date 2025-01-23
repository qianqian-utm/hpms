package com.hpms.controller;

import com.hpms.model.MedicalRecord;
import com.hpms.service.IUserService;
import com.hpms.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    private static final String PATIENTS_ATTRIBUTE = "patients";
    private static final String DOCTORS_ATTRIBUTE = "doctors";
    private static final String MEDICAL_RECORD_ATTRIBUTE = "medicalRecord";
    private static final String ADD_MEDICAL_RECORD_VIEW = "addMedicalRecord";
    private static final String EDIT_MEDICAL_RECORD_VIEW = "editMedicalRecord";
    private static final String REDIRECT_MEDICAL_RECORDS = "redirect:/medicalRecords";

    private final MedicalRecordService medicalRecordService;
    @Autowired
	private IUserService userService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public String viewMedicalRecords(Model model) {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medicalrecords";
    }

    @GetMapping("/add")
    public String addMedicalRecordForm(Model model) {
        model.addAttribute(MEDICAL_RECORD_ATTRIBUTE, new MedicalRecord());
        addUserAttributesToModel(model);
        return ADD_MEDICAL_RECORD_VIEW;
    }

    @PostMapping("/add")
    public String addMedicalRecord(@Valid @ModelAttribute MedicalRecord medicalRecord,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            addUserAttributesToModel(model);
            return ADD_MEDICAL_RECORD_VIEW;
        }
        
        try {
            medicalRecordService.addMedicalRecord(medicalRecord);
        } catch (Exception e) {
            e.printStackTrace();
            addUserAttributesToModel(model);
            return ADD_MEDICAL_RECORD_VIEW;
        }
        return REDIRECT_MEDICAL_RECORDS;
    }

    @GetMapping("/edit/{id}")
    public String editMedicalRecordForm(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        if (medicalRecord == null) {
            return "error";
        }
        model.addAttribute(MEDICAL_RECORD_ATTRIBUTE, medicalRecord);
        addUserAttributesToModel(model);
        return EDIT_MEDICAL_RECORD_VIEW;
    }

    @PostMapping("/edit/{id}")
    public String editMedicalRecord(@PathVariable int id,
                                  @Valid @ModelAttribute MedicalRecord medicalRecord,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            addUserAttributesToModel(model);
            return EDIT_MEDICAL_RECORD_VIEW;
        }
        medicalRecord.setId(id);
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return REDIRECT_MEDICAL_RECORDS;
    }

    @PostMapping("/delete/{id}")
    public String deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return REDIRECT_MEDICAL_RECORDS;
    }

    private void addUserAttributesToModel(Model model) {
        model.addAttribute(PATIENTS_ATTRIBUTE, userService.getPatients());
        model.addAttribute(DOCTORS_ATTRIBUTE, userService.getDoctors());
    }
}