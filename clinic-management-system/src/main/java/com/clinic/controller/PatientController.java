package com.clinic.controller;

import com.clinic.model.Patient;
import com.clinic.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public String listPatients(@RequestParam(required = false) String search, Model model) {
        List<Patient> patients = (search == null || search.isBlank())
                ? patientRepository.findAll()
                : patientRepository.findByNameContainingIgnoreCase(search);
        model.addAttribute("patients", patients);
        model.addAttribute("search", search);
        return "patients";
    }

    @GetMapping("/new")
    public String newPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient_form";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id: " + id));
        model.addAttribute("patient", patient);
        return "patient_form";
    }

    @PostMapping("/save")
    public String savePatient(@Valid @ModelAttribute("patient") Patient patient,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "patient_form";
        }
        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
}
