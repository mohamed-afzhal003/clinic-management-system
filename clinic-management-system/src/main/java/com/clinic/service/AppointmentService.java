package com.clinic.service;

import com.clinic.model.Appointment;
import com.clinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * Checks whether the doctor already has an appointment at the exact same
     * date and time before a new booking is confirmed. This prevents
     * double-booking a doctor's schedule.
     */
    public boolean hasConflict(Appointment newAppointment) {
        List<Appointment> existing = appointmentRepository.findByDoctorIdAndAppointmentDateAndAppointmentTime(
                newAppointment.getDoctor().getId(),
                newAppointment.getAppointmentDate(),
                newAppointment.getAppointmentTime()
        );
        return !existing.isEmpty();
    }

    public Appointment bookAppointment(Appointment appointment) throws IllegalStateException {
        if (hasConflict(appointment)) {
            throw new IllegalStateException(
                    "This doctor already has an appointment booked at that date and time. Please choose a different slot.");
        }
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, java.time.LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
    }
}
