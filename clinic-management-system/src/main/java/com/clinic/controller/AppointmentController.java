package com.clinic.controller;

import com.clinic.model.Appointment;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.PatientRepository;
import com.clinic.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "appointments";
    }

    @GetMapping("/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointment_form";
    }

    @PostMapping("/save")
    public String saveAppointment(@RequestParam Long patientId,
                                   @RequestParam Long doctorId,
                                   @RequestParam String appointmentDate,
                                   @RequestParam String appointmentTime,
                                   @RequestParam(required = false) String reason,
                                   Model model) {

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(patientId).orElseThrow());
        appointment.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        appointment.setAppointmentDate(java.time.LocalDate.parse(appointmentDate));
        appointment.setAppointmentTime(java.time.LocalTime.parse(appointmentTime));
        appointment.setReason(reason);

        try {
            appointmentService.bookAppointment(appointment);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("appointment", appointment);
            model.addAttribute("patients", patientRepository.findAll());
            model.addAttribute("doctors", doctorRepository.findAll());
            return "appointment_form";
        }

        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/appointments";
    }
}
